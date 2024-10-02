import {useContext, useEffect, useState} from "react";
import {CustomContext} from "../../utils/Context";
import Order from "../../pages/orders/Order";
import Navbar from "../../navbar/Navbar";
import PageName from "../../pages/PageName";

const SellerOrdersFinish = () => {

    const user = useContext(CustomContext);
    const [order, setOrder] = useState();
    const [trackNumber, setTrackNumber] = useState();
    const [PIN, setPIN] = useState();

    useEffect(() => {
        const fetchOrders = async () => {
            try {
                const response = await fetch(`http://localhost:8081/api/v1/seller/isReadyOrder?trackNumber=${trackNumber}`);
                if (response.ok) {
                    const result = await response.json();
                    setOrder(result);
                } else {
                    console.error("Ошибка сервера")
                }
            } catch (error) {
                console.error("Ошибка сервера: " + error);
            }
        };
        fetchOrders()
    }, [trackNumber])


    const bthClick = async (orderId) => {
        try {
            const requestOptions = {
                method: "POST"
            }
            const resonse = await fetch(`http://localhost:8081/api/v1/seller/makeOrderFinished?sellerId=${user?.user?.id}&orderId=${orderId}&pin=${PIN}`, requestOptions);
            if (resonse.ok) {
                alert("Заказ усешно оформлен и может быть выдан покупателю")
                window.location.reload();
            } else {
                const result = await resonse.text();
                alert("Неверный PIN")
                console.error("Ошибка сети: " + result);
            }
        } catch (error) {
            console.error("Ошибка сети: " + error);
        }
    }
    return (
        <div>
            <Navbar/>
            <div className='page-content'>
                <PageName name={'Заказы, готовые к выдаче'}/>
                <div className='width_search'>
                    <input
                        type="text"
                        value={trackNumber}
                        onChange={(e) => setTrackNumber(e.target.value)}
                        className='input_search_string'
                        placeholder="Введите трек-номер товара из 10 цифр..."
                    />
                </div>

                {order && <div className='order' style={{justifyContent: "space-between"}}>
                    <Order key={order?.id} order={order}/>
                    <div>
                        <input
                            type={"number"}
                            placeholder={"PIN"}
                            value={PIN}
                            onChange={(e) => setPIN(e.target.value)}
                            className='input_search_string'
                        />
                        <button style={{marginTop: "10px"}} className='big_button' onClick={() => bthClick(order?.id)}>Подтвердить выдачу заказа</button>
                    </div>
                </div>
                }
            </div>
        </div>
    )

}

export default SellerOrdersFinish;