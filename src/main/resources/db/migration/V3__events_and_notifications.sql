ALTER TABLE places ADD COLUMN event_starts_at TIMESTAMPTZ;
ALTER TABLE places ADD COLUMN event_ends_at TIMESTAMPTZ;
-- Existing sample events remain unscheduled until an admin supplies real dates.
ALTER TABLE places ADD CONSTRAINT ck_event_dates CHECK (
 (event_starts_at IS NULL AND event_ends_at IS NULL)
 OR (event_starts_at IS NOT NULL AND event_ends_at IS NOT NULL AND event_ends_at > event_starts_at)
);
CREATE INDEX ix_places_events_schedule ON places(event_starts_at,event_ends_at) WHERE category='event';
CREATE TABLE notifications (
 id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
 user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
 category VARCHAR(20) NOT NULL CHECK(category IN ('EVENTS','REVIEWS','NEWS')),
 title VARCHAR(120) NOT NULL,
 message VARCHAR(1000) NOT NULL,
 target_url VARCHAR(500),
 read_at TIMESTAMPTZ,
 created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE INDEX ix_notifications_user_created ON notifications(user_id,created_at DESC);
ALTER TABLE notifications ENABLE ROW LEVEL SECURITY;