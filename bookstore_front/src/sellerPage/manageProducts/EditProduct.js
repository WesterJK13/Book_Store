import {useParams} from "react-router";
import {useEffect, useState} from "react";
import Navbar from "../../navbar/Navbar";
import PageName from "../../pages/PageName";
import EditProductForm from "./EditProductForm";

const EditProduct = () => {

    const productId = useParams();
    const [product, setProduct] = useState();

    useEffect(() => {
        const fetchProduct = async () => {
            const response = await fetch(`http://localhost:8081/api/v1/general/product?productId=${productId?.productId}`);
            if (response.ok) {
                const result = await response.json();
                setProduct(result);
            } else {
                const result = await response.json();
                console.error("Ошибка сети: " + result);
            }
        };
        fetchProduct();
    }, [productId])

    return (
        <div>
            <Navbar/>
            <div className={'page-content'}>
                <PageName name={'Редактирование пользователя'}/>
                {product && <EditProductForm product={product} productId={productId?.productId}/>}
            </div>
        </div>
    )

}

export default EditProduct