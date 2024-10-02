import Navbar from "../../navbar/Navbar";
import PageName from "../../pages/PageName";
import CreateSellerForm from "./CreateSellerForm";

const CreateSeller = () => {


    return (
        <div>
            <Navbar/>
            <div className={'page-content'}>
                <PageName name={'Создание продавца'}/>
                <CreateSellerForm/>
            </div>
        </div>
    )
}

export default CreateSeller;