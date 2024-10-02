import Profile from "./pages/Profile";
import {Route, Routes} from "react-router";
import Login from "./authorization/Login";
import AboutUs from "./pages/AboutUs";
import Catalog from "./pages/products/Catalog";
import RegistrationForm from "./authorization/RegistrationForm";
import ProductInfo from "./pages/products/ProductInfo";
import Favourites from "./customersPage/Favourites";
import Basket from "./customersPage/Basket";
import Orders from "./customersPage/Orders";
import Library from "./customersPage/Library";
import ReviewForm from "./customersPage/ReviewForm";
import Friends from "./customersPage/Friends";
import UsersNotFriends from "./customersPage/UsersNotFriends";
import FriendApplications from "./customersPage/FriendApplications";
import CustomersPage from "./adminsPage/manageCustomersPage/CustomersPage";
import EditCustomerPageMain from "./adminsPage/manageCustomersPage/EditCustomerPageMain";
import EditSellerPageMain from "./adminsPage/manageSellersPage/EditSellerPageMain";
import SellerPage from "./adminsPage/manageSellersPage/SellerPage";
import CreateSeller from "./adminsPage/manageSellersPage/CreateSeller";
import AdminAndSellerProducts from "./sellerPage/manageProducts/AdminAndSellerProducts";
import CreateProduct from "./sellerPage/manageProducts/CreateProduct";
import EditProduct from "./sellerPage/manageProducts/EditProduct";
import CreateAuthor from "./sellerPage/manageProducts/CreateAuthor";
import SellerOrders from "./sellerPage/manageOrders/SellerOrders";
import SellerOrdersFinish from "./sellerPage/manageOrders/SellerOrdersFinish";
import FriendLibrary from "./customersPage/FriendLibrary";

function App() {

    return (
        <div className="app">
            <Routes>
                <Route exac path='/' element={<Catalog/>}/>
                <Route exac path='/aboutUs' element={<AboutUs/>}/>
                <Route exac path='/adminProducts' element={<AdminAndSellerProducts/>}/>
                <Route exac path='/basket' element={<Basket/>}/>
                <Route exac path='/catalog' element={<Catalog/>}/>
                <Route exac path='/createAuthor' element={<CreateAuthor/>}/>
                <Route exac path='/createProduct' element={<CreateProduct/>}/>
                <Route exac path='/createSeller' element={<CreateSeller/>}/>
                <Route exac path='/customerOrders' element={<Orders/>}/>
                <Route eaxc path='/customers' element={<CustomersPage/>}/>
                <Route exac path='/editCustomer/:customerId' element={<EditCustomerPageMain/>}/>
                <Route exac path='/editProduct/:productId' element={<EditProduct/>}/>
                <Route exac path='/editSeller/:sellerId' element={<EditSellerPageMain/>}/>
                <Route exac path='/favorites' element={<Favourites/>}/>
                <Route exac path='/friendApplications' element={<FriendApplications/>}/>
                <Route exac path='/friendLibrary/:friendId/:friendSurname' element={<FriendLibrary/>}/>
                <Route exac path='/friends' element={<Friends/>}/>
                <Route exac path='/library' element={<Library/>}/>
                <Route exac path='/login' element={<Login/>}/>
                <Route exac path='/makeReview/:productId/:productName' element={<ReviewForm/>}/>
                <Route exac path='/notFriends' element={<UsersNotFriends/>}/>
                <Route exac path='/product/:productId/:makeReview' element={<ProductInfo/>}/>
                <Route exac path='/profile' element={<Profile/>}/>
                <Route exac path='/registration' element={<RegistrationForm/>}/>
                <Route exac path='/sellers' element={<SellerPage/>}/>
                <Route exac path='sellerOrders' element={<SellerOrders/>}/>
                <Route exac path='sellerOrdersFinish' element={<SellerOrdersFinish/>}/>
            </Routes>
        </div>
    );
}

export default App;
