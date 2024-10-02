import {useNavigate, useParams} from "react-router";
import {useContext, useState} from "react";
import Navbar from "../navbar/Navbar";
import PageName from "../pages/PageName";
import {useFormik} from "formik";
import {CustomContext} from "../utils/Context";

const ReviewForm = () => {

    const user = useContext(CustomContext);
    const {productId, productName} = useParams();
    const navigate = useNavigate();

    const formik = useFormik({
        initialValues: {
            commentForAll: '',
            commentForFriends: ''
        },
        onSubmit: async values => {
            const requestOptions = {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    commentForAll: values.commentForAll,
                    commentForFriends: values.commentForFriends
                })
            };

            try {
                const response = await fetch(`http://localhost:8081/api/v1/customer/writeReview?customerId=${user?.user?.id}&productId=${productId}`, requestOptions)
                if (response.ok) {
                    const result = await response.text();
                    alert(result);
                    navigate(`/product/${productId}/${true}`)
                } else {
                    const result = await response.text();
                    console.error(result);
                }
            } catch (error) {
                console.error("Ошибка сети: " + error);
            }
        }
    })

    return (
        <div>
            <Navbar/>
            <div className='page-content'>
                <PageName name={"Отзыв для товара: " + productName}/>
                <div>
                    <form className='form' onSubmit={formik.handleSubmit}>
                        <div>
                            <p className='form_label_text'>Введите отзыв, который будет отображаться у всех
                                пользователей</p>
                            <textarea
                                name='commentForAll'
                                rows='10'
                                cols='100'
                                onChange={formik.handleChange}
                                onBlur={formik.handleBlur}
                                value={formik.values.commentForAll}
                            ></textarea>
                        </div>

                        <div>
                            <p className='form_label_text'>Введите отзыв, который будет отображаться только у ваших
                                друзей</p>
                            <textarea
                                name='commentForFriends'
                                rows='10'
                                cols='100'
                                onChange={formik.handleChange}
                                onBlur={formik.handleBlur}
                                value={formik.values.commentForFriends}
                            ></textarea>
                        </div>

                        <div className='form-button' style={{marginTop: "20px"}}>
                            <button className='button' type='submit'>
                                Отправить
                            </button>
                        </div>

                    </form>
                </div>

            </div>
        </div>
    )

}

export default ReviewForm;