import Navbar from "../navbar/Navbar";
import PageName from "../pages/PageName";
import {useContext, useEffect, useState} from "react";
import {CustomContext} from "../utils/Context";
import OrderList from "../pages/orders/OrderList";

const Orders = () => {

    const user = useContext(CustomContext);
    const [orders, setOrders] = useState();

    useEffect(() => {
        const fetchOrders = async () => {
            try {
                const response = await fetch(`http://localhost:8081/api/v1/customer/myOrders?customerId=${user?.user?.id}`)
                if (response.ok) {
                    const result = await response.json();
                    setOrders(result);
                } else {
                    const error = await response.json();
                    console.log("Ошибка сервера: " + error);
                }
            } catch (error) {
                console.error(error);
            }
        };
        fetchOrders();
    }, [user])

    return (
        <div>
            <Navbar/>
            <div className='page-content'>
                <PageName name={"Заказы"}/>
                <OrderList orders={orders}/>
            </div>
        </div>
    )

}

export default Orders;