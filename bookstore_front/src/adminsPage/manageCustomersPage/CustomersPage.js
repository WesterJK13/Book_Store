import {useEffect, useState} from "react";
import Navbar from "../../navbar/Navbar";
import PageName from "../../pages/PageName";
import CustomerForSeller from "../../sellerPage/CustomerForSeller";

const CustomersPage = () => {

    const [customers, setCustomers] = useState([]);

    useEffect(() => {
        const fetchCustomers = async () => {
            try {
                const response = await fetch(`http://localhost:8081/api/v1/seller/allCustomers`);
                if (response.ok) {
                    const result = await response.json();
                    setCustomers(result);
                } else {
                    console.error("Ошибка сервера")
                }
            } catch (error) {
                console.error("Ошибка сети: " + error);
            }
        };
        fetchCustomers();
    }, [])

    const printCustomer = (customer) => {
        return (
            <CustomerForSeller customer={customer} flagEdit={true} flagDelete={true}/>
        )
    }

    return (
        <div>
            <Navbar/>
            <div className='page-content'>
                <PageName name={'Пользователи системы'}/>
                {customers && customers.map((customer) => {
                        return printCustomer(customer);
                    })
                }
            </div>
        </div>
    )

}

export default CustomersPage;