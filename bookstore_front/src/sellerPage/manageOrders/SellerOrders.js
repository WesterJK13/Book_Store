import {useContext, useEffect, useState} from "react";
import {CustomContext} from "../../utils/Context";
import Navbar from "../../navbar/Navbar";
import PageName from "../../pages/PageName";
import Product from "../../pages/products/Product";
import Order from "../../pages/orders/Order";

const SellerOrders = () => {

    const user = useContext(CustomContext);
    const [orders, setOrders] = useState();

    useEffect(() => {
        const fetchOrders = async () => {
            try {
                const response = await fetch(`http://localhost:8081/api/v1/seller/preparingOrders`);
                if (response.ok) {
                    const result = await response.json();
                    setOrders(result);
                } else {
                    console.error("Ошибка сервера")
                }
            } catch (error) {
                console.error("Ошибка сервера: " + error);
            }
        };
        fetchOrders();
    }, [user])


    const bthClick = async (id) => {
        try {
            const requestOptions = {
                method: "POST"
            }
            const response = await fetch(`http://localhost:8081/api/v1/seller/makeOrderIsReady?sellerId=${user?.user?.id}&orderId=${id}`, requestOptions);
            if (response.ok) {
                alert("Заказ подтвержден")
                window.location.reload();
            } else {
                const result = await response.text();
                alert(result);
            }
        } catch (error) {
            console.error("Ошибка сети: " + error);
        }
    }


    function listOrders(order) {
        return (
            <div className='order' style={{justifyContent: "space-between"}}>
                <Order key={order?.id} order={order}/>
                <button className='big_button' onClick={() => bthClick(order?.id)}>Перевести заказ в статус Готов к получению</button>
            </div>
        )
    }

    console.log(orders);

    return (
        <div>
            <Navbar/>
            <div className='page-content'>
                <PageName name={'Заказы, требующие сборки'}/>
                {orders  && orders.map((order) => {
                    return listOrders(order);
                })}
            </div>
        </div>
    )


}

export default SellerOrders;