CREATE SEQUENCE seq_book START WITH 1 INCREMENT BY 1;


create TABLE book
(
    id BIGINT NOT NULL default nextval('seq_book'),
    created_on TIMESTAMP WITH TIME ZONE NOT NULL default current_timestamp,
    last_modified_on TIMESTAMP WITH TIME ZONE NOT NULL default current_timestamp,
    book_name VARCHAR(255) NOT NULL,
    available BOOLEAN NOT NULL default true,
    publication_date date null
);

ALTER TABLE book ADD CONSTRAINT pk_book PRIMARY KEY (id);

commit;
