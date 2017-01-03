use mytest;


CREATE TABLE tag (
    t_id INT PRIMARY KEY AUTO_INCREMENT,
    t_name VARCHAR(20),
    t_count INT
);

CREATE TABLE favorite (
    f_id INT PRIMARY KEY AUTO_INCREMENT,
    f_label VARCHAR(20),
    f_url VARCHAR(50),
    f_tags VARCHAR(50),
    f_desc VARCHAR(50)
);


select * from favorite;
select * from tag;
insert into tag(t_name,t_count) values('java',2);
insert into tag(t_name,t_count) values('strtua',1);
insert into tag(t_name,t_count) values('oracle',1);

insert into favorite(f_label,f_url,f_tags,f_desc) values('apache Strtua','http://www.apache.org/struts/','java,struts','struts官方站点');
insert into favorite(f_label,f_url,f_tags,f_desc) values('oracle','http://www.oracle.com','java,oracle','oracle官方网站');
















