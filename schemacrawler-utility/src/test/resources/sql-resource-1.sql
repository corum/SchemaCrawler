CREATE TABLE TABLE2
(
  ENTITY_ID INTEGER NOT NULL,
  COL1 CHAR(25),
  COL2 VARCHAR(25),
  COL3 NUMERIC,
  PRIMARY KEY (ENTITY_ID)
);

INSERT INTO TABLE2 (ENTITY_ID, COL1, COL2, COL3)
VALUES (1, 'ABC', 'DEF', 2);

INSERT INTO TABLE2 (ENTITY_ID, COL1, COL2, COL3)
VALUES (2, 'XYZ', 'PQR', NULL);
