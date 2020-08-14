CREATE OR REPLACE PROCEDURE RESET_SEQUENCE
(
    P_TABLE_NAME IN VARCHAR2,
    P_ID_COLUMN IN VARCHAR2,
    P_SEQUENCE_NAME IN VARCHAR2
)
IS
    vSeqObjCount NUMBER;
    vSql VARCHAR2(32767);
    vIdMax NUMBER;
    vSeqVal NUMBER;
    vDiff NUMBER;
BEGIN
    SELECT COUNT(1) INTO vSeqObjCount FROM USER_SEQUENCES WHERE SEQUENCE_NAME = P_SEQUENCE_NAME;
    DBMS_OUTPUT.PUT_LINE('シーケンスの存在: ' || vSeqObjCount);

    IF (vSeqObjCount = 0) THEN
        DBMS_OUTPUT.PUT_LINE('シーケンス「' || P_SEQUENCE_NAME || '」を作成');
        vSql := 'CREATE SEQUENCE ' || P_SEQUENCE_NAME || ' INCREMENT BY 50';
        EXECUTE IMMEDIATE vSql;
        RETURN;
    END IF;

    DBMS_OUTPUT.PUT_LINE('シーケンス「' || P_SEQUENCE_NAME || '」の調整');
    
    vSql := 'SELECT NVL(MAX(' || P_ID_COLUMN || '), 1) FROM ' || P_TABLE_NAME;
    EXECUTE IMMEDIATE vSql INTO vIdMax;
    DBMS_OUTPUT.PUT_LINE('テーブルの最大ID: ' || vIdMax);

    vSql := 'SELECT ' || P_SEQUENCE_NAME || '.nextval FROM DUAL';
    EXECUTE IMMEDIATE vSql INTO vSeqVal;
    DBMS_OUTPUT.PUT_LINE('シーケンスの値: ' || vSeqVal);

    vDiff := vIdMax - vSeqVal;
    IF (vDiff = 0) THEN
        DBMS_OUTPUT.PUT_LINE('シーケンスの調整不要');
        RETURN;
    END IF;

    vSql := 'ALTER SEQUENCE ' || P_SEQUENCE_NAME || ' INCREMENT BY ' || vDiff;
    EXECUTE IMMEDIATE vSql;
    
    vSql := 'SELECT ' || P_SEQUENCE_NAME || '.nextval FROM DUAL';
    EXECUTE IMMEDIATE vSql INTO vSeqVal;
    DBMS_OUTPUT.PUT_LINE('調整後のシーケンスの値: ' || vSeqVal);

    vSql := 'ALTER SEQUENCE ' || P_SEQUENCE_NAME || ' INCREMENT BY 50';
    EXECUTE IMMEDIATE vSql;
END;
