set schema PUBLIC;

create table rrs (
    id BIGINT NOT NULL,-- DEFAULT NEXTVAL(('"rrs_id_seq"' :: TEXT) :: REGCLASS),
    title VARCHAR(100) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    uri VARCHAR(200) NOT NULL,
    published_date TIMESTAMP WITH TIME ZONE
);

ALTER TABLE rrs
    ADD CONSTRAINT pk_rrs_id
        PRIMARY KEY (id);

CREATE SEQUENCE rrs_id_seq INCREMENT 1 START 1;