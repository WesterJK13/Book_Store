import {formatDateLocal} from "../../utils/date-utils";

const Order = ({order}) => {

    const printProductsName = (products) => {
        return products.map((product, index) => {
            if (index === products.length - 1) {
                return product.name;
            } else {
                return product.name + ", "
            }
        })
    }

    const printOrderStatus = (status) => {
        if (status === "PREPARING") {
            return "В сборке"
        } else if (status === "IS_READY") {
            return "Готов к получению!"
        } else {
            return "Оплачен и получен"
        }
    }



    return(
        <div>
            <div>
                <p className='str'>
                    <strong>Трек-номер заказа: </strong>{order?.trackNumber}
                </p>
                <p className='str'>
                    <strong>PIN-для получения заказа: </strong>{order?.pin}
                </p>
                <p className='str'>
                    <strong>Цена: </strong>{order?.totalPrice}
                </p>
                <p className='str'>
                    <strong>Дата создания: </strong>{order?.dateCreate && formatDateLocal(order?.dateCreate)}
                </p>
                <p className='str'>
                    <strong>Статус: </strong>{printOrderStatus(order?.orderStatus)}
                </p>
                <p className='str'>
                    <strong>Список товаров в заказе: </strong>{printProductsName(order?.productList)}
                </p>
            </div>
        </div>

    )

}

export default Order;