import Navbar from "../../navbar/Navbar";
import PageName from "../../pages/PageName";
import CreateProductForm from "./CreateProductForm";

const CreateProduct = () => {

    return (
        <div>
            <Navbar/>
            <div className={'page-content'}>
                <PageName name={'Создание продукта'}/>
                <CreateProductForm/>
            </div>
        </div>
    )

}

export default CreateProduct;