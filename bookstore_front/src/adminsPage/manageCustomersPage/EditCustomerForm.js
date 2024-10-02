import {useNavigate, useParams} from "react-router";
import {useFormik} from "formik";
import * as Yup from 'yup';

const EditCustomerForm = ({customer, customerId}) => {

    const navigate = useNavigate();

    const formik = useFormik({
        initialValues: {
            name: customer?.name,
            patronymic: customer?.patronymic,
            surname: customer?.surname,
            phone: customer?.phone
        },
        validationSchema: Yup.object({
            name: Yup.string()
                .required("Поле обязательно для заполнения"),
            surname: Yup.string()
                .required("Поле обязательно для заполнения"),
            phone: Yup.string()
                .required("Поле обязательно для заполнения"),
        }),
        onSubmit: async values => {
            const requestOptions = {
                method: 'PUT',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    name: values.name,
                    patronymic: values.patronymic,
                    surname: values.surname,
                    phone: values.phone
                }),
            };

            try {
                const response = await fetch(`http://localhost:8081/api/v1/seller/editCustomer?customerId=${customerId}`, requestOptions)
                if (response.ok) {
                    const result = await response.text();
                    alert(result);
                    navigate('/customers')
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
                        Введите имя пользователя:
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
                        Введите отчество пользователя:
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
                        Введите фамилию пользоватлея:
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

                <div className='field_block_in_form'>
                    <p className='some_info'>
                        Введите телефон пользователя:
                    </p>
                    <input
                        type="tel"
                        name='phone'
                        className='input_auth fonts-roboto-light'
                        style={{fontSize: "18px"}}
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                        value={formik.values.phone}
                    />
                    {formik.touched.phone && formik.errors.phone ? (
                        <div className='error-massage'>{formik.errors.phone}</div>
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

export default EditCustomerForm