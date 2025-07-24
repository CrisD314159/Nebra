ALTER TABLE comment
    ADD answered BOOLEAN;

ALTER TABLE comment
    ALTER COLUMN answered SET NOT NULL;
