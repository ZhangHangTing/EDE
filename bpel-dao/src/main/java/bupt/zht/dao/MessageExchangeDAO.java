package bupt.zht.dao;

public interface MessageExchangeDAO {
    MessageDAO getResponse();
    MessageDAO getRequest();
}
