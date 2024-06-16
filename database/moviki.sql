CREATE TABLE IF NOT EXISTS "client" (
	"id" bigint GENERATED ALWAYS AS IDENTITY NOT NULL UNIQUE,
	"name" text NOT NULL UNIQUE,
	"email" text NOT NULL UNIQUE,
	"password_hash" text NOT NULL,
	"profile_picture_path" text,
	"created_at" timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "role" (
	"id" bigint GENERATED ALWAYS AS IDENTITY NOT NULL UNIQUE,
	"name" text NOT NULL UNIQUE,
	"description" text NOT NULL,
	PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "client_role" (
	"client_id" bigint NOT NULL,
	"role_id" bigint NOT NULL
);

CREATE TABLE IF NOT EXISTS "article" (
	"id" bigint GENERATED ALWAYS AS IDENTITY NOT NULL UNIQUE,
	"client_id" bigint NOT NULL,
	"title" text NOT NULL UNIQUE,
	"content" text NOT NULL,
	"main_image_path" text,
	"created_at" timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
	"updated_at" timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "comment" (
	"id" bigint GENERATED ALWAYS AS IDENTITY NOT NULL UNIQUE,
	"article_id" bigint NOT NULL,
	"client_id" bigint NOT NULL,
	"content" text NOT NULL,
	"created_at" timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY ("id")
);

ALTER TABLE "client_role" ADD CONSTRAINT "client_role_fk0" FOREIGN KEY ("client_id") REFERENCES "client"("id");
ALTER TABLE "client_role" ADD CONSTRAINT "client_role_fk1" FOREIGN KEY ("role_id") REFERENCES "role"("id");
ALTER TABLE "article" ADD CONSTRAINT "article_fk1" FOREIGN KEY ("client_id") REFERENCES "client"("id");
ALTER TABLE "comment" ADD CONSTRAINT "comment_fk1" FOREIGN KEY ("article_id") REFERENCES "article"("id");
ALTER TABLE "comment" ADD CONSTRAINT "comment_fk2" FOREIGN KEY ("client_id") REFERENCES "client"("id");

CREATE  FUNCTION update_updated_at_article()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = now();
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_article_updated_at
    BEFORE UPDATE
    ON
        article
    FOR EACH ROW
EXECUTE PROCEDURE update_updated_at_article();
