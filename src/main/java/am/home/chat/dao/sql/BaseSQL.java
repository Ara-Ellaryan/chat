package am.home.chat.dao.sql;

import am.home.chat.utils.db.ConnectionFactory;

abstract class BaseSQL {
    protected ConnectionFactory connectionFactory;

    BaseSQL(){
        this.connectionFactory = ConnectionFactory.getInstance();
    }
}
