/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.mesgui.server;

import dy.fi.maja.mesgui.models.Order;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author k1400284
 */
public interface OrderRepository extends MongoRepository<Order, Long>
{
    public Order findByoNo(Long orderNumber);
}
