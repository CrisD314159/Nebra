CREATE EXTENSION IF NOT EXISTS postgis;

CREATE SEQUENCE IF NOT EXISTS schedule_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE business
(
    id             UUID NOT NULL,
    description    VARCHAR(255),
    name           VARCHAR(255),
    phone_contact  VARCHAR(255),
    category       SMALLINT,
    date_created   TIMESTAMP WITHOUT TIME ZONE,
    location       GEOMETRY(Point, 4326),
    user_owner_id  UUID,
    business_state SMALLINT,
    CONSTRAINT pk_business PRIMARY KEY (id)
);

CREATE TABLE business_comments
(
    business_id UUID NOT NULL,
    comments_id UUID NOT NULL
);

CREATE TABLE business_images
(
    business_id UUID         NOT NULL,
    images_id   VARCHAR(255) NOT NULL
);

CREATE TABLE business_revisions_list
(
    business_id       UUID NOT NULL,
    revisions_list_id UUID NOT NULL
);

CREATE TABLE business_schedule_list
(
    business_id      UUID   NOT NULL,
    schedule_list_id BIGINT NOT NULL
);

CREATE TABLE comment
(
    id           UUID    NOT NULL,
    title        VARCHAR(255),
    score        INTEGER NOT NULL,
    content      VARCHAR(255),
    date_created TIMESTAMP WITHOUT TIME ZONE,
    author_id    UUID,
    business_id  UUID,
    answer       VARCHAR(255),
    CONSTRAINT pk_comment PRIMARY KEY (id)
);

CREATE TABLE image
(
    id   VARCHAR(255) NOT NULL,
    link VARCHAR(255),
    CONSTRAINT pk_image PRIMARY KEY (id)
);

CREATE TABLE report
(
    id           UUID         NOT NULL,
    user_id      UUID,
    business_id  UUID,
    reason       VARCHAR(255) NOT NULL,
    report_state SMALLINT,
    answer       VARCHAR(255),
    CONSTRAINT pk_report PRIMARY KEY (id)
);

CREATE TABLE revision
(
    id           UUID NOT NULL,
    state        SMALLINT,
    moderator_id UUID,
    business_id  UUID,
    reason       VARCHAR(255),
    CONSTRAINT pk_revision PRIMARY KEY (id)
);

CREATE TABLE schedule
(
    id         BIGINT NOT NULL,
    day        VARCHAR(255),
    open_hour  VARCHAR(255),
    close_hour VARCHAR(255),
    CONSTRAINT pk_schedule PRIMARY KEY (id)
);

CREATE TABLE session
(
    id         UUID NOT NULL,
    user_id    UUID,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    expires_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_session PRIMARY KEY (id)
);

CREATE TABLE users
(
    id                     UUID         NOT NULL,
    name                   VARCHAR(255) NOT NULL,
    email                  VARCHAR(255) NOT NULL,
    password               VARCHAR(255) NOT NULL,
    profile_picture_id     VARCHAR(255),
    location               VARCHAR(255),
    user_state             SMALLINT,
    user_role              SMALLINT,
    is_third_party_user    BOOLEAN      NOT NULL,
    verification_code      VARCHAR(255),
    recovery_account_token VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE users_business_list
(
    user_id          UUID NOT NULL,
    business_list_id UUID NOT NULL
);

CREATE TABLE users_comments
(
    user_id     UUID NOT NULL,
    comments_id UUID NOT NULL
);

CREATE TABLE users_favorite_business
(
    user_id              UUID NOT NULL,
    favorite_business_id UUID NOT NULL
);

CREATE TABLE users_reports
(
    user_id    UUID NOT NULL,
    reports_id UUID NOT NULL
);

ALTER TABLE business_comments
    ADD CONSTRAINT uc_business_comments_comments UNIQUE (comments_id);

ALTER TABLE business_images
    ADD CONSTRAINT uc_business_images_images UNIQUE (images_id);

ALTER TABLE business_revisions_list
    ADD CONSTRAINT uc_business_revisions_list_revisionslist UNIQUE (revisions_list_id);

ALTER TABLE business_schedule_list
    ADD CONSTRAINT uc_business_schedule_list_schedulelist UNIQUE (schedule_list_id);

ALTER TABLE users_business_list
    ADD CONSTRAINT uc_users_business_list_businesslist UNIQUE (business_list_id);

ALTER TABLE users_comments
    ADD CONSTRAINT uc_users_comments_comments UNIQUE (comments_id);

ALTER TABLE users_favorite_business
    ADD CONSTRAINT uc_users_favorite_business_favoritebusiness UNIQUE (favorite_business_id);

ALTER TABLE users_reports
    ADD CONSTRAINT uc_users_reports_reports UNIQUE (reports_id);

ALTER TABLE business
    ADD CONSTRAINT FK_BUSINESS_ON_USEROWNER FOREIGN KEY (user_owner_id) REFERENCES users (id);

ALTER TABLE comment
    ADD CONSTRAINT FK_COMMENT_ON_AUTHOR FOREIGN KEY (author_id) REFERENCES users (id);

ALTER TABLE comment
    ADD CONSTRAINT FK_COMMENT_ON_BUSINESS FOREIGN KEY (business_id) REFERENCES business (id);

ALTER TABLE report
    ADD CONSTRAINT FK_REPORT_ON_BUSINESS FOREIGN KEY (business_id) REFERENCES business (id);

ALTER TABLE report
    ADD CONSTRAINT FK_REPORT_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE revision
    ADD CONSTRAINT FK_REVISION_ON_BUSINESS FOREIGN KEY (business_id) REFERENCES business (id);

ALTER TABLE revision
    ADD CONSTRAINT FK_REVISION_ON_MODERATOR FOREIGN KEY (moderator_id) REFERENCES users (id);

ALTER TABLE session
    ADD CONSTRAINT FK_SESSION_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_PROFILEPICTURE FOREIGN KEY (profile_picture_id) REFERENCES image (id);

ALTER TABLE business_comments
    ADD CONSTRAINT fk_buscom_on_business FOREIGN KEY (business_id) REFERENCES business (id);

ALTER TABLE business_comments
    ADD CONSTRAINT fk_buscom_on_comment FOREIGN KEY (comments_id) REFERENCES comment (id);

ALTER TABLE business_images
    ADD CONSTRAINT fk_busima_on_business FOREIGN KEY (business_id) REFERENCES business (id);

ALTER TABLE business_images
    ADD CONSTRAINT fk_busima_on_image FOREIGN KEY (images_id) REFERENCES image (id);

ALTER TABLE business_revisions_list
    ADD CONSTRAINT fk_busrevlis_on_business FOREIGN KEY (business_id) REFERENCES business (id);

ALTER TABLE business_revisions_list
    ADD CONSTRAINT fk_busrevlis_on_revision FOREIGN KEY (revisions_list_id) REFERENCES revision (id);

ALTER TABLE business_schedule_list
    ADD CONSTRAINT fk_busschlis_on_business FOREIGN KEY (business_id) REFERENCES business (id);

ALTER TABLE business_schedule_list
    ADD CONSTRAINT fk_busschlis_on_schedule FOREIGN KEY (schedule_list_id) REFERENCES schedule (id);

ALTER TABLE users_business_list
    ADD CONSTRAINT fk_usebuslis_on_business FOREIGN KEY (business_list_id) REFERENCES business (id);

ALTER TABLE users_business_list
    ADD CONSTRAINT fk_usebuslis_on_user FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE users_comments
    ADD CONSTRAINT fk_usecom_on_comment FOREIGN KEY (comments_id) REFERENCES comment (id);

ALTER TABLE users_comments
    ADD CONSTRAINT fk_usecom_on_user FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE users_favorite_business
    ADD CONSTRAINT fk_usefavbus_on_business FOREIGN KEY (favorite_business_id) REFERENCES business (id);

ALTER TABLE users_favorite_business
    ADD CONSTRAINT fk_usefavbus_on_user FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE users_reports
    ADD CONSTRAINT fk_userep_on_report FOREIGN KEY (reports_id) REFERENCES report (id);

ALTER TABLE users_reports
    ADD CONSTRAINT fk_userep_on_user FOREIGN KEY (user_id) REFERENCES users (id);