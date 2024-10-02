import Order from "./Order";

const OrderList = ({orders}) => {


    const listOrder = (order) => {
        return (
            <div className='order'>
                <Order key={order?.id} order={order}/>
            </div>
        )
    }

    return (
        <div className='products'>
            <div className='container_for_products'>
                <div className='list-items '>
                    {orders && orders.map((order) => {
                        return listOrder(order);
                    })}
                </div>
            </div>
        </div>
    )
}

export default OrderList;