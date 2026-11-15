-- City Guide schema. Tables are owned by the application database role.
CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE users (
 id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
 name VARCHAR(100) NOT NULL,
 email VARCHAR(255) NOT NULL,
 password_hash VARCHAR(100) NOT NULL,
 phone VARCHAR(40) NOT NULL DEFAULT '',
 avatar_url TEXT,
 role VARCHAR(10) NOT NULL DEFAULT 'USER' CHECK (role IN ('USER','ADMIN')),
 notif_events BOOLEAN NOT NULL DEFAULT TRUE,
 notif_reviews BOOLEAN NOT NULL DEFAULT TRUE,
 notif_news BOOLEAN NOT NULL DEFAULT FALSE,
 created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
 updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE UNIQUE INDEX ux_users_email ON users (lower(email));

CREATE TABLE cities (
 id VARCHAR(40) PRIMARY KEY,
 name VARCHAR(100) NOT NULL,
 country VARCHAR(100) NOT NULL,
 lat DOUBLE PRECISION NOT NULL CHECK (lat BETWEEN -90 AND 90),
 lng DOUBLE PRECISION NOT NULL CHECK (lng BETWEEN -180 AND 180),
 timezone VARCHAR(60) NOT NULL,
 hue INTEGER NOT NULL DEFAULT 200,
 blurb VARCHAR(300) NOT NULL,
 description TEXT NOT NULL,
 image_url TEXT,
 created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
 updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE places (
 id VARCHAR(40) PRIMARY KEY,
 city_id VARCHAR(40) NOT NULL REFERENCES cities(id),
 category VARCHAR(20) NOT NULL CHECK (category IN ('attraction','restaurant','hotel','event')),
 name VARCHAR(150) NOT NULL,
 description TEXT NOT NULL,
 address VARCHAR(255) NOT NULL,
 phone VARCHAR(40) NOT NULL DEFAULT '',
 website VARCHAR(255) NOT NULL DEFAULT '',
 lat DOUBLE PRECISION NOT NULL CHECK (lat BETWEEN -90 AND 90),
 lng DOUBLE PRECISION NOT NULL CHECK (lng BETWEEN -180 AND 180),
 opens_at TIME,
 closes_at TIME,
 open_24h BOOLEAN NOT NULL DEFAULT FALSE,
 closed_days SMALLINT[],
 hours_text VARCHAR(120) NOT NULL,
 extra VARCHAR(120) NOT NULL DEFAULT '',
 base_rating DOUBLE PRECISION NOT NULL CHECK (base_rating BETWEEN 1 AND 5),
 rating_avg DOUBLE PRECISION NOT NULL CHECK (rating_avg BETWEEN 1 AND 5),
 review_count INTEGER NOT NULL DEFAULT 0 CHECK (review_count >= 0),
 created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
 updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
 CHECK ((opens_at IS NULL) = (closes_at IS NULL))
);
CREATE INDEX ix_places_city_category ON places(city_id,category);
CREATE INDEX ix_places_rating ON places(rating_avg DESC);

CREATE TABLE place_images (
 id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
 place_id VARCHAR(40) NOT NULL REFERENCES places(id) ON DELETE CASCADE,
 url TEXT NOT NULL,
 sort_order INTEGER NOT NULL DEFAULT 0,
 created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE INDEX ix_place_images_place ON place_images(place_id,sort_order);

CREATE TABLE reviews (
 id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
 place_id VARCHAR(40) NOT NULL REFERENCES places(id) ON DELETE CASCADE,
 user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
 rating SMALLINT NOT NULL CHECK (rating BETWEEN 1 AND 5),
 text VARCHAR(600) NOT NULL,
 created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
 updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
 CONSTRAINT uq_reviews_place_user UNIQUE(place_id,user_id)
);
CREATE INDEX ix_reviews_place_created ON reviews(place_id,created_at DESC);
CREATE INDEX ix_reviews_user ON reviews(user_id);

CREATE TABLE review_likes (
 review_id UUID NOT NULL REFERENCES reviews(id) ON DELETE CASCADE,
 user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
 created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
 PRIMARY KEY(review_id,user_id)
);
CREATE INDEX ix_review_likes_user ON review_likes(user_id);

CREATE TABLE favorites (
 user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
 place_id VARCHAR(40) NOT NULL REFERENCES places(id) ON DELETE CASCADE,
 created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
 PRIMARY KEY(user_id,place_id)
);
CREATE INDEX ix_favorites_place ON favorites(place_id);

CREATE TABLE refresh_tokens (
 id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
 user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
 token_hash VARCHAR(64) NOT NULL UNIQUE,
 expires_at TIMESTAMPTZ NOT NULL,
 revoked BOOLEAN NOT NULL DEFAULT FALSE,
 created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE INDEX ix_refresh_tokens_user ON refresh_tokens(user_id);

CREATE TABLE password_reset_tokens (
 id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
 user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
 token_hash VARCHAR(64) NOT NULL,
 expires_at TIMESTAMPTZ NOT NULL,
 used_at TIMESTAMPTZ,
 attempts INTEGER NOT NULL DEFAULT 0,
 created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE INDEX ix_reset_tokens_user ON password_reset_tokens(user_id);

ALTER TABLE users ENABLE ROW LEVEL SECURITY;
ALTER TABLE cities ENABLE ROW LEVEL SECURITY;
ALTER TABLE places ENABLE ROW LEVEL SECURITY;
ALTER TABLE place_images ENABLE ROW LEVEL SECURITY;
ALTER TABLE reviews ENABLE ROW LEVEL SECURITY;
ALTER TABLE review_likes ENABLE ROW LEVEL SECURITY;
ALTER TABLE favorites ENABLE ROW LEVEL SECURITY;
ALTER TABLE refresh_tokens ENABLE ROW LEVEL SECURITY;
ALTER TABLE password_reset_tokens ENABLE ROW LEVEL SECURITY;
