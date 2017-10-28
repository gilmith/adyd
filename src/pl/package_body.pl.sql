/* Formatted on 28/10/2017 18:36:07 (QP5 v5.227.12220.39754) */
CREATE OR REPLACE PACKAGE BODY MASTER.ADYD_PKG
AS
   FUNCTION INSERTMODULO (V_NOMBRE IN ADYD_MODULOS.TIPO%TYPE)
      RETURN NUMBER
   IS
      --en las funciones de paquete no llevan el declare solo si son funciones sueltas
      N_ID   NUMBER;
   BEGIN
      SELECT COUNT (ID)
        INTO N_ID
        FROM ADYD_COLECCION
       WHERE NOMBRE = V_NOMBRE;

      IF (N_ID >= 1)
      THEN
         RETURN 0;
      ELSE
         SELECT COUNT (ID)
           INTO N_ID
           FROM ADYD_MODULOS
          WHERE TIPO = V_NOMBRE;

         IF (N_ID >= 1)
         THEN
            sys.DBMS_OUTPUT.put_line ('el modulo ya existe');
            RETURN 0;
         ELSE
            INSERT INTO ADYD_MODULOS (ID, TIPO)
                 VALUES (ADYD_MODULOS_SEQ.NEXTVAL, V_NOMBRE);

            IF (SQL%ROWCOUNT = 1)
            THEN
               COMMIT;
               N_ID := ADYD_MODULOS_SEQ.CURRVAL;
               RETURN N_ID;
            ELSE
               ROLLBACK;
               RETURN 0;
            END IF;
         END IF;
      END IF;
   EXCEPTION
      WHEN NO_DATA_FOUND
      THEN
         RAISE_APPLICATION_ERROR (-20000, 'MODULO YA INSERTADO');
         RETURN 0;
      WHEN TOO_MANY_ROWS
      THEN
         RAISE_APPLICATION_ERROR (-20000, 'MODULO YA INSERTADO');
   END INSERTMODULO;


   PROCEDURE INSERTFILE (
      V_FILE           IN     ADYD_FILES.NOMBRE%TYPE,
      V_RUTA           IN     ADYD_FILES.RUTA%TYPE,
      N_TAMANO         IN     ADYD_FILES.TAMANHO%TYPE,
      V_TSR_ID1        IN     ADYD_TSR.TSR_ID1%TYPE,
      V_TSR_ID2        IN     ADYD_TSR.TSR_ID2%TYPE,
      V_RUTA_MINI      IN     ADYD_MINI.PATH%TYPE,
      N_COLECCION_ID   IN     ADYD_FILES.COLECCION_ID_FK%TYPE,
      N_CODE              OUT NUMBER,
      V_MESSAGE           OUT VARCHAR)
   IS
      --AQUI TENGO QUE DEFINIR LOS ID DE SECUENCIA DE CADA TABLA
      N_ID_LAST_MODULO   NUMBER;
      N_ID_NEXT_EXTRA    NUMBER;
      N_ID_NEXT_MINI     NUMBER;
      N_ID_FILE          NUMBER;
      N_ID_MINI          NUMBER;
      N_TSR1             NUMBER;
      N_TSR2             NUMBER;
   BEGIN
      SELECT MAX (ID) INTO N_ID_LAST_MODULO FROM ADYD_MODULOS;

      SELECT COUNT (ID)
        INTO N_ID_FILE
        FROM ADYD_FILES
       WHERE NOMBRE = V_FILE;

      -- SELECCION DE PRECONDICIONES
      SELECT COUNT (TSR_ID1)
        INTO N_TSR1
        FROM ADYD_TSR
       WHERE TSR_ID1 = V_TSR_ID1;

      SELECT COUNT (ID)
        INTO N_ID_MINI
        FROM ADYD_MINI
       WHERE PATH = V_RUTA_MINI;

      --CONDICIONES
      IF ( (N_ID_FILE >= 1) OR (N_TSR1 >= 1) OR (N_ID_MINI >= 1))
      THEN
         V_MESSAGE := 'Archivo ya insertado';
         N_CODE := -1;
      ELSE
         --FALTAN PRECONDICIONES!!! METERLAS COMO METODOS!
         SELECT ADYD_EXTRA_SEQ.NEXTVAL INTO N_ID_NEXT_EXTRA FROM DUAL;

         SELECT ADYD_MINI_SEQ.NEXTVAL INTO N_ID_NEXT_MINI FROM DUAL;

         --METO EN LA TABLA TSR ID
         INSERT INTO ADYD_TSR (TSR_ID1, TSR_ID2)
              VALUES (V_TSR_ID1, V_TSR_ID2);

         --MINIATURA
         INSERT INTO ADYD_MINI (ID, PATH)
              VALUES (N_ID_NEXT_MINI, V_RUTA_MINI);

         --ARCHIVO
         INSERT INTO ADYD_FILES (ID,
                                 COLECCION_ID_FK,
                                 TSR_ID_FK,
                                 NOMBRE,
                                 TAMANHO,
                                 RUTA,
                                 ID_MINI_FK,
                                 ID_EXTRA_FK,
                                 ID_MODULO_FK)
              VALUES (ADYD_FILES_SEQ.NEXTVAL,
                      N_COLECCION_ID,
                      V_TSR_ID1,
                      V_FILE,
                      N_TAMANO,
                      V_RUTA,
                      N_ID_NEXT_MINI,
                      NULL,
                      N_ID_LAST_MODULO);

         COMMIT;
         V_MESSAGE := 'Archivo insertado';
         N_CODE := 0;
      END IF;
   EXCEPTION
      WHEN NO_DATA_FOUND
      THEN
         RAISE_APPLICATION_ERROR ('-20000', 'SIN DATOS');
      WHEN DUP_VAL_ON_INDEX
      THEN
         RAISE_APPLICATION_ERROR ('-20002', 'DATOS DUPLICADOS');
      WHEN OTHERS
      THEN
         RAISE_APPLICATION_ERROR ('-20001', 'ERROR FATAL');
   END INSERTFILE;

   FUNCTION GETCOLECCION (V_COLECCION IN ADYD_COLECCION.NOMBRE%TYPE)
      RETURN NUMBER
   IS
      N_ID   NUMBER;
   BEGIN
      SELECT COUNT (*)
        INTO N_ID
        FROM ADYD_COLECCION
       WHERE NOMBRE = V_COLECCION;

      IF N_ID >= 1
      THEN
         SELECT ID
           INTO N_ID
           FROM ADYD_COLECCION
          WHERE NOMBRE = V_COLECCION;

         RETURN N_ID;
      ELSE
         RETURN 0;
      END IF;
   EXCEPTION
      WHEN NO_DATA_FOUND
      THEN
         RAISE_APPLICATION_ERROR ('-20000', 'ERROR GENERICO COMPROBAR DATOS');
      WHEN OTHERS
      THEN
         RAISE_APPLICATION_ERROR ('-20001', 'ERROR FATAL');
   END GETCOLECCION;

   FUNCTION GETFILE (V_FILE IN ADYD_FILES.NOMBRE%TYPE)
      RETURN NUMBER
   IS
      N_ID   NUMBER;
   BEGIN
      N_ID := -1;

      SELECT COUNT (ID)
        INTO N_ID
        FROM ADYD_FILES
       WHERE NOMBRE LIKE '%' || V_FILE || '%';

      RETURN N_ID;
   END GETFILE;



   PROCEDURE GET_FILES_INFO (V_NOMBRE    IN     ADYD_FILES.NOMBRE%TYPE,
                             CODIGO         OUT NUMBER,
                             RESULTADO      OUT VARCHAR2,
                             ID             OUT TBL_OUTPUT,
                             TSR_ID         OUT TBL_OUTPUT,
                             TSR_ID2        OUT TBL_OUTPUT,
                             NOMBRE         OUT TBL_OUTPUT,
                             COLECCION      OUT TBL_OUTPUT,
                             MODULO         OUT TBL_OUTPUT,
                             RUTA           OUT TBL_OUTPUT,
                             TAMANHO        OUT TBL_OUTPUT)
   IS
      CURSOR C_FILE (
         V_NOMBRE1 IN ADYD_FILES.NOMBRE%TYPE)
      IS
         SELECT FILES.ID,
                FILES.TSR_ID_FK TSR_ID,
                CASE WHEN TSR.TSR_ID2 = 'NULL' THEN '' ELSE TSR.TSR_ID2 END
                   TSR_ID2,
                FILES.NOMBRE,
                COLECCION.NOMBRE COLECCION,
                MODULOS.TIPO MODULO,
                FILES.RUTA,
                FILES.TAMANHO
           FROM ADYD_FILES FILES
                INNER JOIN ADYD_COLECCION COLECCION
                   ON (FILES.COLECCION_ID_FK = COLECCION.ID)
                INNER JOIN ADYD_MODULOS MODULOS
                   ON (FILES.ID_MODULO_FK = MODULOS.ID)
                INNER JOIN ADYD_TSR TSR ON (FILES.TSR_ID_FK = TSR.TSR_ID1)
          WHERE FILES.NOMBRE LIKE '%' || V_NOMBRE1 || '%';


      TYPE TYPE_FILE IS RECORD
      (
         ID          ADYD_FILES.ID%TYPE,
         TSR_ID      ADYD_FILES.TSR_ID_FK%TYPE,
         TSR_ID2     ADYD_TSR.TSR_ID2%TYPE,
         NOMBRE      ADYD_FILES.NOMBRE%TYPE,
         COLECCION   ADYD_COLECCION.NOMBRE%TYPE,
         MODULO      ADYD_MODULOS.TIPO%TYPE,
         RUTA        ADYD_FILES.RUTA%TYPE,
         TAMANHO     ADYD_FILES.TAMANHO%TYPE
      );


      TYPE T_TYPE_FILE IS TABLE OF TYPE_FILE
         INDEX BY BINARY_INTEGER;

      DATOS   T_TYPE_FILE;
   BEGIN
      --DEFINICION
      OPEN C_FILE (V_NOMBRE);

      ID := TBL_OUTPUT ();
      TSR_ID := TBL_OUTPUT ();
      TSR_ID2 := TBL_OUTPUT ();
      COLECCION := TBL_OUTPUT ();
      MODULO := TBL_OUTPUT ();
      NOMBRE := TBL_OUTPUT ();
      RUTA := TBL_OUTPUT ();
      TAMANHO := TBL_OUTPUT ();

      FETCH C_FILE
         BULK COLLECT INTO DATOS;

      BEGIN
         IF (C_FILE%ROWCOUNT <> 0)
         THEN
            CODIGO := 0;
            RESULTADO := 'OK';
            --INICIALIZACION
            ID.EXTEND (C_FILE%ROWCOUNT);
            TSR_ID.EXTEND (C_FILE%ROWCOUNT);
            TSR_ID2.EXTEND (C_FILE%ROWCOUNT);
            NOMBRE.EXTEND (C_FILE%ROWCOUNT);
            COLECCION.EXTEND (C_FILE%ROWCOUNT);
            MODULO.EXTEND (C_FILE%ROWCOUNT);
            RUTA.EXTEND (C_FILE%ROWCOUNT);
            TAMANHO.EXTEND (C_FILE%ROWCOUNT);

            FOR I IN DATOS.FIRST .. DATOS.LAST
            LOOP
               SYS.DBMS_OUTPUT.PUT_LINE (
                     DATOS (I).ID
                  || ' '
                  || DATOS (I).TSR_ID
                  || ' '
                  || DATOS (I).TSR_ID2
                  || ' '
                  || DATOS (I).NOMBRE
                  || ' '
                  || DATOS (I).COLECCION
                  || ' '
                  || DATOS (I).MODULO);
               ID (I) := TO_CHAR (DATOS (I).ID);
               TSR_ID (I) := TO_CHAR (DATOS (I).TSR_ID);
               TSR_ID2 (I) := TO_CHAR (DATOS (I).TSR_ID2);
               NOMBRE (I) := DATOS (I).NOMBRE;
               COLECCION (I) := DATOS (I).COLECCION;
               MODULO (I) := DATOS (I).MODULO;
               RUTA (I) := DATOS (I).RUTA;
               TAMANHO (I) := DATOS (I).TAMANHO;
            END LOOP;
         ELSE
            CODIGO := -1;
            RESULTADO := 'KO';
         END IF;
      END;
   EXCEPTION
      WHEN OTHERS
      THEN
         RAISE_APPLICATION_ERROR ('-20000', 'ERROR AL GENERAR EL OUTPUT');
   END GET_FILES_INFO;


   PROCEDURE GET_ALL_MODULOS (V_TIPO    IN     ADYD_MODULOS.TIPO%TYPE,
                              CODIGO         OUT NUMBER,
                              RESULTADO      OUT VARCHAR2,
                              ID             OUT TBL_OUTPUT,
                              TSR_ID         OUT TBL_OUTPUT,
                              TSR_ID2        OUT TBL_OUTPUT,
                              NOMBRE         OUT TBL_OUTPUT,
                              COLECCION      OUT TBL_OUTPUT,
                              MODULO         OUT TBL_OUTPUT,
                              RUTA           OUT TBL_OUTPUT,
                              TAMANHO        OUT TBL_OUTPUT)
   IS
      CURSOR C_MODULO (
         V_TIPO IN ADYD_MODULOS.TIPO%TYPE)
      IS
         SELECT FILES.ID,
                FILES.TSR_ID_FK TSR_ID,
                CASE WHEN TSR.TSR_ID2 = 'NULL' THEN '' ELSE TSR.TSR_ID2 END
                   TSR_ID2,
                FILES.NOMBRE,
                COLECCION.NOMBRE COLECCION,
                MODULOS.TIPO MODULO,
                FILES.RUTA,
                FILES.TAMANHO
           FROM ADYD_FILES FILES
                INNER JOIN ADYD_COLECCION COLECCION
                   ON (FILES.COLECCION_ID_FK = COLECCION.ID)
                INNER JOIN ADYD_MODULOS MODULOS
                   ON (FILES.ID_MODULO_FK = MODULOS.ID)
                INNER JOIN ADYD_TSR TSR ON (FILES.TSR_ID_FK = TSR.TSR_ID1)
          WHERE MODULOS.TIPO LIKE '%' || V_TIPO || '%';

      TYPE TYPE_FILE IS RECORD
      (
         ID          ADYD_FILES.ID%TYPE,
         TSR_ID      ADYD_FILES.TSR_ID_FK%TYPE,
         TSR_ID2     ADYD_TSR.TSR_ID2%TYPE,
         NOMBRE      ADYD_FILES.NOMBRE%TYPE,
         COLECCION   ADYD_COLECCION.NOMBRE%TYPE,
         MODULO      ADYD_MODULOS.TIPO%TYPE,
         RUTA        ADYD_FILES.RUTA%TYPE,
         TAMANHO     ADYD_FILES.TAMANHO%TYPE
      );


      TYPE T_TYPE_FILE IS TABLE OF TYPE_FILE
         INDEX BY BINARY_INTEGER;

      DATOS   T_TYPE_FILE;
   BEGIN
      --DEFINICION
      OPEN C_MODULO (V_TIPO);

      ID := TBL_OUTPUT ();
      TSR_ID := TBL_OUTPUT ();
      TSR_ID2 := TBL_OUTPUT ();
      COLECCION := TBL_OUTPUT ();
      MODULO := TBL_OUTPUT ();
      NOMBRE := TBL_OUTPUT ();
      RUTA := TBL_OUTPUT ();
      TAMANHO := TBL_OUTPUT ();

      FETCH C_MODULO
         BULK COLLECT INTO DATOS;

      BEGIN
         IF (C_MODULO%ROWCOUNT <> 0)
         THEN
            CODIGO := 0;
            RESULTADO := 'OK';
            --INICIALIZACION
            ID.EXTEND (C_MODULO%ROWCOUNT);
            TSR_ID.EXTEND (C_MODULO%ROWCOUNT);
            TSR_ID2.EXTEND (C_MODULO%ROWCOUNT);
            NOMBRE.EXTEND (C_MODULO%ROWCOUNT);
            COLECCION.EXTEND (C_MODULO%ROWCOUNT);
            MODULO.EXTEND (C_MODULO%ROWCOUNT);
            RUTA.EXTEND (C_MODULO%ROWCOUNT);
            TAMANHO.EXTEND (C_MODULO%ROWCOUNT);

            FOR I IN DATOS.FIRST .. DATOS.LAST
            LOOP
               SYS.DBMS_OUTPUT.PUT_LINE (
                     DATOS (I).ID
                  || ' '
                  || DATOS (I).TSR_ID
                  || ' '
                  || DATOS (I).TSR_ID2
                  || ' '
                  || DATOS (I).NOMBRE
                  || ' '
                  || DATOS (I).COLECCION
                  || ' '
                  || DATOS (I).MODULO);
               ID (I) := TO_CHAR (DATOS (I).ID);
               TSR_ID (I) := TO_CHAR (DATOS (I).TSR_ID);
               TSR_ID2 (I) := TO_CHAR (DATOS (I).TSR_ID2);
               NOMBRE (I) := DATOS (I).NOMBRE;
               COLECCION (I) := DATOS (I).COLECCION;
               MODULO (I) := DATOS (I).MODULO;
               RUTA (I) := DATOS (I).RUTA;
               TAMANHO (I) := DATOS (I).TAMANHO;
            END LOOP;
         ELSE
            CODIGO := -1;
            RESULTADO := 'KO';
         END IF;
      END;
   EXCEPTION
      WHEN OTHERS
      THEN
         RAISE_APPLICATION_ERROR ('-20001', 'ERROR AL GENERAR EL OUTPUT');
END GET_ALL_MODULOS;


   PROCEDURE GET_ALL_COLECCION (V_NOMBRE      IN     ADYD_COLECCION.NOMBRE%TYPE,
                                CODIGO         OUT NUMBER,
                                RESULTADO      OUT VARCHAR2,
                                ID             OUT TBL_OUTPUT,
                                TSR_ID         OUT TBL_OUTPUT,
                                TSR_ID2        OUT TBL_OUTPUT,
                                NOMBRE         OUT TBL_OUTPUT,
                                COLECCION      OUT TBL_OUTPUT,
                                MODULO         OUT TBL_OUTPUT,
                                RUTA           OUT TBL_OUTPUT,
                                TAMANHO        OUT TBL_OUTPUT)
   IS
      CURSOR C_COLECCION (
         V_NOMBRE IN ADYD_COLECCION.NOMBRE%TYPE)
      IS
         SELECT FILES.ID,
                FILES.TSR_ID_FK TSR_ID,
                CASE WHEN TSR.TSR_ID2 = 'NULL' THEN '' ELSE TSR.TSR_ID2 END
                   TSR_ID2,
                FILES.NOMBRE,
                COLECCION.NOMBRE COLECCION,
                MODULOS.TIPO MODULO,
                FILES.RUTA,
                FILES.TAMANHO
           FROM ADYD_FILES FILES
                INNER JOIN ADYD_COLECCION COLECCION
                   ON (FILES.COLECCION_ID_FK = COLECCION.ID)
                INNER JOIN ADYD_MODULOS MODULOS
                   ON (FILES.ID_MODULO_FK = MODULOS.ID)
                INNER JOIN ADYD_TSR TSR ON (FILES.TSR_ID_FK = TSR.TSR_ID1)
          WHERE COLECCION.NOMBRE LIKE '%' || V_NOMBRE || '%';

      TYPE TYPE_FILE IS RECORD
      (
         ID          ADYD_FILES.ID%TYPE,
         TSR_ID      ADYD_FILES.TSR_ID_FK%TYPE,
         TSR_ID2     ADYD_TSR.TSR_ID2%TYPE,
         NOMBRE      ADYD_FILES.NOMBRE%TYPE,
         COLECCION   ADYD_COLECCION.NOMBRE%TYPE,
         MODULO      ADYD_MODULOS.TIPO%TYPE,
         RUTA        ADYD_FILES.RUTA%TYPE,
         TAMANHO     ADYD_FILES.TAMANHO%TYPE
      );


      TYPE T_TYPE_FILE IS TABLE OF TYPE_FILE
         INDEX BY BINARY_INTEGER;

      DATOS   T_TYPE_FILE;
   BEGIN
      --DEFINICION
      OPEN C_COLECCION (V_NOMBRE);

      ID := TBL_OUTPUT ();
      TSR_ID := TBL_OUTPUT ();
      TSR_ID2 := TBL_OUTPUT ();
      COLECCION := TBL_OUTPUT ();
      MODULO := TBL_OUTPUT ();
      NOMBRE := TBL_OUTPUT ();
      RUTA := TBL_OUTPUT ();
      TAMANHO := TBL_OUTPUT ();

      FETCH C_COLECCION
         BULK COLLECT INTO DATOS;

      BEGIN
         IF (C_COLECCION%ROWCOUNT <> 0)
         THEN
            CODIGO := 0;
            RESULTADO := 'OK';
            --INICIALIZACION
            ID.EXTEND (C_COLECCION%ROWCOUNT);
            TSR_ID.EXTEND (C_COLECCION%ROWCOUNT);
            TSR_ID2.EXTEND (C_COLECCION%ROWCOUNT);
            NOMBRE.EXTEND (C_COLECCION%ROWCOUNT);
            COLECCION.EXTEND (C_COLECCION%ROWCOUNT);
            MODULO.EXTEND (C_COLECCION%ROWCOUNT);
            RUTA.EXTEND (C_COLECCION%ROWCOUNT);
            TAMANHO.EXTEND (C_COLECCION%ROWCOUNT);

            FOR I IN DATOS.FIRST .. DATOS.LAST
            LOOP
               SYS.DBMS_OUTPUT.PUT_LINE (
                     DATOS (I).ID
                  || ' '
                  || DATOS (I).TSR_ID
                  || ' '
                  || DATOS (I).TSR_ID2
                  || ' '
                  || DATOS (I).NOMBRE
                  || ' '
                  || DATOS (I).COLECCION
                  || ' '
                  || DATOS (I).MODULO);
               ID (I) := TO_CHAR (DATOS (I).ID);
               TSR_ID (I) := TO_CHAR (DATOS (I).TSR_ID);
               TSR_ID2 (I) := TO_CHAR (DATOS (I).TSR_ID2);
               NOMBRE (I) := DATOS (I).NOMBRE;
               COLECCION (I) := DATOS (I).COLECCION;
               MODULO (I) := DATOS (I).MODULO;
               RUTA (I) := DATOS (I).RUTA;
               TAMANHO (I) := DATOS (I).TAMANHO;
            END LOOP;
         ELSE
            CODIGO := -1;
            RESULTADO := 'KO';
         END IF;
      END;
   EXCEPTION
      WHEN OTHERS
      THEN
         RAISE_APPLICATION_ERROR ('-20001', 'ERROR AL GENERAR EL OUTPUT');
   END GET_ALL_COLECCION;
END ADYD_PKG;
/
