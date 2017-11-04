CREATE OR REPLACE PACKAGE MASTER.ADYD_PKG
IS
   FUNCTION INSERTMODULO (V_NOMBRE IN ADYD_MODULOS.TIPO%TYPE)
      RETURN NUMBER;


   FUNCTION GETCOLECCION (V_COLECCION IN ADYD_COLECCION.NOMBRE%TYPE)
      RETURN NUMBER;

   FUNCTION GETFILE (V_FILE IN ADYD_FILES.NOMBRE%TYPE)
      RETURN NUMBER;

   PROCEDURE INSERTFILE (
      V_FILE           IN     ADYD_FILES.NOMBRE%TYPE,
      V_RUTA           IN     ADYD_FILES.RUTA%TYPE,
      N_TAMANO         IN     ADYD_FILES.TAMANHO%TYPE,
      V_TSR_ID1        IN     ADYD_TSR.TSR_ID1%TYPE,
      V_TSR_ID2        IN     ADYD_TSR.TSR_ID2%TYPE,
      V_RUTA_MINI      IN     ADYD_MINI.PATH%TYPE,
      N_COLECCION_ID   IN     ADYD_FILES.COLECCION_ID_FK%TYPE,
      N_CODE              OUT NUMBER,
      V_MESSAGE           OUT VARCHAR);

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
                             TAMANHO        OUT TBL_OUTPUT);

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
                              TAMANHO        OUT TBL_OUTPUT);


   PROCEDURE GET_ALL_COLECCION (V_NOMBRE    IN     ADYD_COLECCION.NOMBRE%TYPE,
                                CODIGO         OUT NUMBER,
                                RESULTADO      OUT VARCHAR2,
                                ID             OUT TBL_OUTPUT,
                                TSR_ID         OUT TBL_OUTPUT,
                                TSR_ID2        OUT TBL_OUTPUT,
                                NOMBRE         OUT TBL_OUTPUT,
                                COLECCION      OUT TBL_OUTPUT,
                                MODULO         OUT TBL_OUTPUT,
                                RUTA           OUT TBL_OUTPUT,
                                TAMANHO        OUT TBL_OUTPUT);
                                
    PROCEDURE GET_FILE_INFO_OBJ(V_NOMBRE IN ADYD_FILES.NOMBRE%TYPE,
                                CODIGO OUT NUMBER,
                                RESULTADO OUT VARCHAR2,
                                ARRAY OUT ARRAY_RESPUESTA);                                 
END ADYD_PKG;
/