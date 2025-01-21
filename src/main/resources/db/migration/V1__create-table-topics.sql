create table topics(

id bigint not null auto_increment,
title varchar(200) not null

    private User author;
    private String course;
    private LocalDateTime dateOfCreation;
    private List<String> messages;
    private boolean status = true;

primarykey(id)
);
