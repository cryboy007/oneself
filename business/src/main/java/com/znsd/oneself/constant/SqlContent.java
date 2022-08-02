package com.znsd.oneself.constant;

/**
 * @enumName SqlContent
 * @Author HETAO
 * @Date 2022/6/28 9:51
 */
public enum SqlContent implements DefualtEnum{
        BOOK_INSERT("增加","INSERT INTO example_test.book (title) VALUES(?);"),
        BOOK_DELETE("删除","DELETE FROM example_test.book WHERE id= ?;"),
        TB_USER_INERT("增加","INSERT INTO example_test.tb_user (username, password) VALUES(?, ?)");
    ;


    private final String desc;
    private final String value;

    SqlContent(String code, String value) {
        this.desc = code;
        this.value = value;
    }

    @Override
    public String getCode() {
        return desc;
    }

    @Override
    public String getValue() {
        return value;
    }
}
