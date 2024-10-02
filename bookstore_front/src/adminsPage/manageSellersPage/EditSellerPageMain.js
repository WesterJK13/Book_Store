import {useParams} from "react-router";
import {useEffect, useState} from "react";
import Navbar from "../../navbar/Navbar";
import PageName from "../../pages/PageName";
import EditSellerForm from "./EditSellerForm";

const EditSellerPageMain = () => {
    const sellerId = useParams();
    const [seller, setSeller] = useState();

    useEffect(() => {
        const fetchSeller = async () => {
            const response = await fetch(`http://localhost:8081/api/v1/admin/getSeller?sellerId=${sellerId?.sellerId}`);
            if (response.ok) {
                const result = await response.json();
                setSeller(result);
            } else {
                const result = await response.json();
                console.error("Ошибка сети: " + result);
            }
        };
        fetchSeller();
    }, [sellerId])

    return (
        <div>
            <Navbar/>
            <div className={'page-content'}>
                <PageName name={'Редактирование продавца'}/>
                {seller && <EditSellerForm seller={seller} sellerId={sellerId?.sellerId}/>}
            </div>
        </div>
    )
}

export default EditSellerPageMain;