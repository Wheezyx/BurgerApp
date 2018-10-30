package burgerapp.components.order;

import burgerapp.components.generic.GenericDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl extends GenericDaoImpl<Order,Long> implements OrderDao
{

}
