import {useNavigate} from "react-router";
import {useFormik} from "formik";
import * as Yup from "yup";

const EditSellerForm = ({seller, sellerId}) => {

    const navigate = useNavigate();

    const formik = useFormik({
        initialValues: {
            name: seller?.name,
            patronymic: seller?.patronymic,
            surname: seller?.surname,
        },
        validationSchema: Yup.object({
            name: Yup.string()
                .required("Поле обязательно для заполнения"),
            surname: Yup.string()
                .required("Поле обязательно для заполнения"),
        }),
        onSubmit: async values => {
            const requestOptions = {
                method: 'PUT',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    email: seller?.email,
                    name: values.name,
                    patronymic: values.patronymic,
                    surname: values.surname,
                }),
            };

            try {
                const response = await fetch(`http://localhost:8081/api/v1/admin/editSeller?sellerId=${sellerId}`, requestOptions)
                if (response.ok) {
                    const result = await response.text();
                    alert(result);
                    navigate('/sellers')
                } else {
                    const errorData = await response.text();
                    alert(errorData);
                }
            } catch (error) {
                console.error("Ошибка сети: " + error);
            }
        }
    })


    return (
        <div>
            <form className='edit_form' onSubmit={formik.handleSubmit}>
                <div className='field_block_in_form'>
                    <p className='some_info'>
                        Введите имя продавца:
                    </p>
                    <input
                        type="text"
                        name='name'
                        className='input_auth fonts-roboto-light'
                        style={{fontSize: "18px"}}
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                        value={formik.values.name}
                    />
                    {formik.touched.name && formik.errors.name ? (
                        <div className='error-massage'>{formik.errors.name}</div>
                    ) : null}
                </div>

                <div className='field_block_in_form'>
                    <p className='some_info'>
                        Введите отчество продавца:
                    </p>
                    <input
                        type="text"
                        name='patronymic'
                        className='input_auth fonts-roboto-light'
                        style={{fontSize: "18px"}}
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                        value={formik.values.patronymic}
                    />
                </div>

                <div className='field_block_in_form'>
                    <p className='some_info'>
                        Введите фамилию продавца:
                    </p>
                    <input
                        type="text"
                        name='surname'
                        className='input_auth fonts-roboto-light'
                        style={{fontSize: "18px"}}
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                        value={formik.values.surname}
                    />
                    {formik.touched.surname && formik.errors.surname ? (
                        <div className='error-massage'>{formik.errors.surname}</div>
                    ) : null}
                </div>

                <div className='form-button'>
                    <button className='button' type='submit'>
                        Отправить
                    </button>
                </div>
            </form>
        </div>
    )


}

export default EditSellerForm;