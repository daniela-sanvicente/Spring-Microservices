insert into Alumnos values ("1A","Juan","Lopez","2001-01-01",1.78, 9);
insert into Alumnos values ("2A","Nadia","Perez","2001-01-10",1.56, 11);
insert into Alumnos values ("3B","Perla","Calles","2001-01-20",1.60, 9);
insert into Alumnos values ("4A","Carlos","Madero","2001-01-01",1.68, 12);
insert into Alumnos values ("5A","Javier","Amaro","2001-02-10",1.75, 12);
insert into Alumnos values ("6C","Jesus","Garcia","2001-03-20",1.65, 3);
insert into Alumnos values ("7B","Gema",null,"2001-03-20",1.53, 3);


insert into Calificaciones values (1, "BD",10,"2A");
insert into Calificaciones values (2,"POO",9,"2A");
insert into Calificaciones values (3,"SI",10,"2A");
insert into Calificaciones values (4,"BDII",8,"2A");
insert into Calificaciones values (5,"BD",9,"3B");
insert into Calificaciones values (6,"POO",5,"3B");
insert into Calificaciones values (7,"BDII",8,"7B");
insert into Calificaciones values (8,"POO",5,"7B");
insert into Calificaciones values (9,"SI",7,"6C");
insert into Calificaciones values (10,"POO",5,"6C");


INSERT INTO Estados
VALUES
('1','Aguascalientes','AGS'), ('2','Baja California','BC'), ('3','Baja California Sur','BCS'), ('4','Campeche','CAMP'), ('5','Coahuila','COAH'), ('6','Colima','COL'), ('7','Chiapas','CHIS'), ('8','Chihuahua','CHIH'), ('9','Ciudad de México','CDMX'), ('10','Durango','DGO'), ('11','Guanajuato','GTO'), ('12','Guerrero','GRO'), ('13','Hidalgo','HGO'), ('14','Jalisco','JAL'), ('15','Estado de México','MEX'), ('16','Michoacán','MICH'), ('17','Morelos','MOR'), ('18','Nayarit','NAY'), ('19','Nuevo León','NL'), ('20','Oaxaca','OAX'), ('21','Puebla','PUE'), ('22','Querétaro','QRO'), ('23','Quintana Roo','QR'), ('24','San Luis Potosí','SLP'), ('25','Sinaloa','SIN'), ('26','Sonora','SON'), ('27','Tabasco','TAB'), ('28','Tamaulipas','TAMP'), ('29','Tlaxcala','TLAX'), ('30','Veracruz','VER'), ('31','Yucatán','YUC'), ('32','Zacatecas','ZAC');

INSERT INTO Grupos (grupo) VALUES
('PRIMERO'), ('SEGUNDO'), ('TERCERO'), ('CUARTO'), ('QUINTO'), ('SEXTO');

INSERT INTO Alumnos_Grupos (matricula, id_grupo, anio) VALUES
('1A', 1, 2020), ('1A', 2, 2021), ('1A', 3, 2022),
('2A', 2, 2020), ('2A', 3, 2021), ('2A', 4, 2022),
('3B', 3, 2020), ('3B', 4, 2021), ('3B', 5, 2022),
('4A', 4, 2020), ('4A', 5, 2021), ('4A', 6, 2022),
('5A', 5, 2020), ('5A', 6, 2021), ('5A', 1, 2022),
('6C', 6, 2020), ('6C', 1, 2021), ('6C', 2, 2022),
('7B', 3, 2020), ('7B', 4, 2021), ('7B', 5, 2022);
