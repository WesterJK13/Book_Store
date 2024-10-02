import {useParams} from "react-router";
import {useEffect, useState} from "react";
import EditCustomerForm from "./EditCustomerForm";
import Navbar from "../../navbar/Navbar";
import PageName from "../../pages/PageName";

const EditCustomerPageMain = () => {

    const customerId = useParams();
    const [customer, setCustomer] = useState();

    useEffect(() => {
        const fetchCustomer = async () => {
            const response = await fetch(`http://localhost:8081/api/v1/general/getCustomer?customerId=${customerId?.customerId}`);
            if (response.ok) {
                const result = await response.json();
                setCustomer(result);
            } else {
                const result = await response.json();
                console.error("Ошибка сети: " + result);
            }
        };
        fetchCustomer();
    }, [customerId])

    return (
        <div>
            <Navbar/>
            <div className={'page-content'}>
                <PageName name={'Редактирование пользователя'}/>
                {customer && <EditCustomerForm customer={customer} customerId={customerId?.customerId}/>}
            </div>
        </div>
    )

}

export default EditCustomerPageMain;