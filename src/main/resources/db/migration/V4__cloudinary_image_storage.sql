ALTER TABLE users ADD COLUMN avatar_public_id VARCHAR(255);
ALTER TABLE cities ADD COLUMN image_public_id VARCHAR(255);
ALTER TABLE place_images ADD COLUMN cloudinary_public_id VARCHAR(255);
