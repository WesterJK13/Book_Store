import {useEffect, useState} from "react";
import CustomerForSeller from "../../sellerPage/CustomerForSeller";
import Navbar from "../../navbar/Navbar";
import PageName from "../../pages/PageName";
import SellerBlock from "./SellerBlock";
import {useNavigate} from "react-router";

const SellerPage = () => {

    const [sellers, setSellers] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchSellers = async () => {
            try {
                const response = await fetch(`http://localhost:8081/api/v1/admin/sellers`);
                if (response.ok) {
                    const result = await response.json();
                    setSellers(result);
                } else {
                    console.error("Ошибка сервера")
                }
            } catch (error) {
                console.error("Ошибка сети: " + error);
            }
        };
        fetchSellers();
    }, [])

    const printSeller = (seller) => {
        return (
            <SellerBlock seller={seller}/>
        )
    }

    const createSeller = () => {
        navigate('/createSeller');
    }

    return (
        <div>
            <Navbar/>
            <div className='page-content'>
                <div className='flex_in_studio' style={{justifyContent: "space-between"}}>
                    <PageName name={'Продавцы'}/>
                    <button className='button' style={{margin: "15px"}} onClick={() => {createSeller()}}>Создать продавца</button>
                </div>
                {sellers && sellers.map((seller) => {
                    return printSeller(seller);
                })
                }
            </div>
        </div>
    )

}

export default SellerPage;