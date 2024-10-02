import Navbar from "../../navbar/Navbar";
import PageName from "../../pages/PageName";
import {useEffect, useState} from "react";
import ProductForAdminAndSeller from "./ProductForAdminAndSeller";
import {useNavigate} from "react-router";

const AdminAndSellerProducts = () => {

    const [products, setProducts] = useState();
    const navigate = useNavigate();

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const response = await fetch(`http://localhost:8081/api/v1/general/allProducts`);
                if (response.ok) {
                    const result = await response.json();
                    setProducts(result);
                } else {
                    const result = await response.json();
                    console.error("Ошибка сети: " + result);
                }
            } catch (error) {
                console.error("Ошибка сети: " + error);
            }
        };
        fetchProducts();
    }, [])

    const printProduct = (product) => {
        return (
            <ProductForAdminAndSeller product={product}/>
        )
    }

    const createProduct = () => {
        navigate('/createProduct')
    }

    const createAuthor = () => {
        navigate('/createAuthor')
    }

    return(
        <div>
            <Navbar/>
            <div className='page-content'>
                <div className='flex_in_studio' style={{justifyContent: "space-between"}}>
                    <PageName name={'Товары'}/>
                    <div>
                        <button className='button' style={{margin: "15px"}} onClick={() => {createProduct()}}>Создать товар</button>
                        <button className='button' style={{margin: "15px"}} onClick={() => {createAuthor()}}>Добавить автора</button>
                    </div>
                </div>
                {products && products.map((product) => {
                    return printProduct(product);
                })}
            </div>
        </div>
    )
}

export default AdminAndSellerProducts;