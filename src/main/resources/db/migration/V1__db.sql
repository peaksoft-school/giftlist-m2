create sequence
    booking_seq start 1 increment 1;
create sequence
    category_seq start 1 increment 1;
create sequence
    charity_seq start 1 increment 1;
create sequence
    complaints_seq start 1 increment 1;
create sequence
    holiday_seq start 1 increment 1;
create sequence
    mailing_list_seq start 1 increment 1;
create sequence
    notification_seq start 1 increment 1;
create sequence
    password_seq start 1 increment 1;
create sequence
    subcategory_seq start 1 increment 1;
create sequence
    user_seq start 3 increment 1;
create sequence
    wish_list_seq start 1 increment 1;

create table booking
(
    id           int8 not null,
    created_at   date,
    charity_id   int8,
    user_id      int8,
    wish_list_id int8,
    primary key (id)
);
create table categories
(
    id            int8 not null,
    category_name varchar(255),
    primary key (id)
);
create table categories_charities
(
    category_id  int8 not null,
    charities_id int8 not null
);
create table charity
(
    id             int8    not null,
    charity_status varchar(255),
    condition      varchar(255),
    created_at     date,
    description    varchar(10000),
    gift_name      varchar(255),
    image          varchar(255),
    is_block       boolean,
    is_blocked     boolean not null,
    category_id    int8,
    subcategory_id int8,
    user_id        int8,
    primary key (id)
);
create table clothing_size
(
    id      int8 not null,
    size    varchar(255),
    user_id int8,
    primary key (id)
);
create table complaints
(
    id              int8 not null,
    complaints_type varchar(255),
    charity_id      int8,
    user_id         int8,
    wish_list_id    int8,
    primary key (id)
);
create table friends
(
    user_id    int8 not null,
    friends_id int8 not null
);
create table holiday
(
    id         int8 not null,
    image      varchar(255),
    local_date date,
    name       varchar(255),
    user_id    int8,
    primary key (id)
);
create table holiday_wish_lists
(
    holiday_id    int8 not null,
    wish_lists_id int8 not null
);
create table mailing_list
(
    id         int8 not null,
    created_at date,
    email      varchar(255),
    header     varchar(255),
    image      varchar(255),
    text       varchar(10000),
    primary key (id)
);
create table notification
(
    id                  int8    not null,
    created_at          date,
    gift_id             int8,
    gift_name           varchar(255),
    is_read             boolean not null,
    notification_status varchar(255),
    charity_id          int8,
    complaints_id       int8,
    user_id             int8,
    wish_list_id        int8,
    primary key (id)
);
create table notification_receivers
(
    notification_list_id int8 not null,
    receivers_id         int8 not null
);
create table passwor_reset_token
(
    id              int8 not null,
    expiration_time timestamp,
    token           varchar(255),
    user_id         int8,
    primary key (id)
);
create table shoe_size
(
    id      int8 not null,
    size    int4 not null,
    user_id int8,
    primary key (id)
);
create table subcategories
(
    id               int8 not null,
    subcategory_name varchar(255),
    category_id      int8,
    primary key (id)
);
create table subcategories_charities
(
    subcategory_id int8 not null,
    charities_id   int8 not null
);
create table users
(
    id                         int8    not null,
    city                       varchar(255),
    date_of_birth              date,
    email                      varchar(255),
    first_name                 varchar(255),
    hobbies                    varchar(10000),
    image                      varchar(255),
    important_to_know          varchar(10000),
    is_block                   boolean,
    is_subscribe_to_newsletter boolean not null,
    last_name                  varchar(255),
    password                   varchar(255),
    phone_number               varchar(255),
    role                       varchar(255),
    primary key (id)
);
create table users_request_to_friends
(
    user_id               int8 not null,
    request_to_friends_id int8 not null
);
create table wish_list
(
    id               int8    not null,
    created          date,
    description      varchar(10000),
    gift_name        varchar(255),
    holyday_date     date,
    image            varchar(255),
    is_block         boolean,
    is_blocked       boolean not null,
    link             varchar(255),
    wish_list_status varchar(255),
    holiday_id       int8,
    user_id          int8,
    primary key (id)
);
alter table if exists categories_charities
    add constraint UK_om21idpskvm0hpg813hpguw7y unique (charities_id);
alter table if exists holiday_wish_lists
    add constraint UK_kpdpwn872479th7i71hwfvruq unique (wish_lists_id);
alter table if exists subcategories_charities
    add constraint UK_5htqcmiabidi94qoe33br3mlt unique (charities_id);
alter table if exists booking
    add constraint FK23m6d3dk9tgjc54wxg4obw62n foreign key (charity_id) references charity;
alter table if exists booking
    add constraint FK7udbel7q86k041591kj6lfmvw foreign key (user_id) references users;
alter table if exists booking
    add constraint FKs1fis2jakwpamwctkph3ca6gg foreign key (wish_list_id) references wish_list;
alter table if exists categories_charities
    add constraint FK1ie8mext5s5qyorqcdxf4eu7p foreign key (charities_id) references charity;
alter table if exists categories_charities
    add constraint FKc349qrv59yxmri1e4pq443jr2 foreign key (category_id) references categories;
alter table if exists charity
    add constraint FKponabnxqllr11bxe462sa9io2 foreign key (category_id) references categories;
alter table if exists charity
    add constraint FKac02wol8ty6yfb85oy1shjoo8 foreign key (subcategory_id) references subcategories;
alter table if exists charity
    add constraint FKck5bwpe6sa5d5yt1cq6w1lcg1 foreign key (user_id) references users;
alter table if exists clothing_size
    add constraint FKjgyjxrlyiqknxgqpi98obpyds foreign key (user_id) references users;
alter table if exists complaints
    add constraint FKoalwblbs4exmr9cyd7ca1atw2 foreign key (charity_id) references charity;
alter table if exists complaints
    add constraint FK83j5gqkd7ku4vc908g4rtmglr foreign key (user_id) references users;
alter table if exists complaints
    add constraint FKdpf7bt8l1hmdqrsnysk59jui8 foreign key (wish_list_id) references wish_list;
alter table if exists friends
    add constraint FK1t6y979j76dre2026t39gimk7 foreign key (friends_id) references users;
alter table if exists friends
    add constraint FKlh21lfp7th1y1tn9g63ihkda9 foreign key (user_id) references users;
alter table if exists holiday
    add constraint FKlob7r339h3li7twmfy32rn99a foreign key (user_id) references users;
alter table if exists holiday_wish_lists
    add constraint FK7whcc9rabgds2jtv8kohdxnrx foreign key (wish_lists_id) references wish_list;
alter table if exists holiday_wish_lists
    add constraint FKf096ix2aj5oea6esk5vaybtt6 foreign key (holiday_id) references holiday;
alter table if exists notification
    add constraint FK6foaai0kj336g1tidgjcge4x5 foreign key (charity_id) references charity;
alter table if exists notification
    add constraint FKb6wvi1bxfu1mcqxe3kliefh2u foreign key (complaints_id) references complaints;
alter table if exists notification
    add constraint FKnk4ftb5am9ubmkv1661h15ds9 foreign key (user_id) references users;
alter table if exists notification
    add constraint FK40pbfm6gu8qs2gycmh8nlclwc foreign key (wish_list_id) references wish_list;
alter table if exists notification_receivers
    add constraint FKk0imokikhny17u1fkbxse7i1b foreign key (receivers_id) references users;
alter table if exists notification_receivers
    add constraint FK33j45u5579jlncx19vr1fkej1 foreign key (notification_list_id) references notification;
alter table if exists passwor_reset_token
    add constraint FKfv5qtamxxc3tddoq7iwkrnhm6 foreign key (user_id) references users;
alter table if exists shoe_size
    add constraint FKndrmf7camtal168t5ca8443ci foreign key (user_id) references users;
alter table if exists subcategories
    add constraint FKiborb6ptvy1t1n3v6klb56l5s foreign key (category_id) references categories;
alter table if exists subcategories_charities
    add constraint FKpfx87mw2lfslma1hrdv06nplg foreign key (charities_id) references charity;
alter table if exists subcategories_charities
    add constraint FKddot8nt0brj05rge6sf8wop2l foreign key (subcategory_id) references subcategories;
alter table if exists users_request_to_friends
    add constraint FKmmr2krwy3lv89ey7et8tkmbi foreign key (request_to_friends_id) references users;
alter table if exists users_request_to_friends
    add constraint FKbjuw1ahtyrp7rwsar1hi6qjbm foreign key (user_id) references users;
alter table if exists wish_list
    add constraint FK7sgnitief3tl27vrj30auqet foreign key (holiday_id) references holiday;
alter table if exists wish_list
    add constraint FKit8ap20bpapw291y78egje6f3 foreign key (user_id) references users;