import Navbar from "../../navbar/Navbar";
import PageName from "../PageName";
import {useEffect, useState} from "react";
import ProductList from "./ProductList";

const Catalog = () => {

    const [products, setProducts] = useState();
    const [name, setName] = useState('');
    const [productList, setProductList] = useState();
    const [pageNumber, setPageNumber] = useState(0);
    const [quantityProducts, setQuantityProducts] = useState();
    const [sortBy, setSortBy] = useState('name');

    useEffect(() => {
        if (productList === undefined) {
            const fetchProducts = async () => {
                try {
                    const response = await fetch(`http://localhost:8081/api/v1/general/products?page=${pageNumber}&size=12&fieldForSort=${sortBy}`)
                    if (response.ok) {
                        const result = await response.json();
                        setProducts(result.content);
                        setQuantityProducts(result.totalElements);
                    } else {
                        const errorData = await response.json();
                        console.error("Ошибка сервера: ", errorData);
                    }
                } catch (error) {
                    console.error(error);
                }
            };
            fetchProducts();
        } else {
            setProducts(productList);
        }
    }, [productList, pageNumber, sortBy])

    const handleSearch = () => {
        if (name) {
            const url = `http://localhost:8081/api/v1/general/productByName?name=${name}`;
            fetch(url)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Ошибка сети');
                    }
                    return response.json();
                })
                .then(data => {
                    setProductList([...data.content]);
                })
                .catch(error => {
                    console.error('Ошибка при выполнении запроса:', error);
                });
        } else {
            setProductList(undefined)
        }
    };

    const printPaginationBlock = (quantityProducts) => {
        const quantityPage = Math.ceil(quantityProducts?.quantityProducts / 12);
        return (
            <div>
                {
                    Array.from({length: quantityPage}, (_, index) => (
                        <button
                        key={index}
                        onClick={() => settingPageNum(index)}
                        style={{margin:'5px', padding: '10px'}}>
                            {index + 1}
                        </button>
                    ))
                }
            </div>
        )
    }

    const settingPageNum = (num) => {
        setPageNumber(num);
    }

    const handleChange = (event) => {
        console.log(event.target.value);
        setSortBy(event.target.value);
    }

    return (
        <div>
            <Navbar/>
            <div className='page-content'>
                <PageName name={"Каталог товаров"}/>
                <div className='width_search'>
                    <input
                        type="text"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        className='input_search_string'
                        placeholder="Введите название товара..."
                    />
                    <button onClick={handleSearch} className='button_search'>
                        Поиск
                    </button>
                    <p style={{fontSize: '20px', padding: '10px'}}><strong>Сортировать по: </strong></p>
                    <select id="sort_product" value={sortBy} onChange={handleChange}>
                        <option value="name">наименованию</option>
                        <option value="price">цене</option>
                        <option value="availability">наличию в магазине</option>
                    </select>
                </div>
                <ProductList products={products} flagBasket={true} flagFavourites={true} flagDeleteFromBasket={false} flagDeleteFromFavourites={false} makeReview={false}/>
                <div className='pagination_block'>
                    <strong>Страницы: </strong> {printPaginationBlock({quantityProducts})}
                </div>
            </div>
        </div>
    )

}

export default Catalog;