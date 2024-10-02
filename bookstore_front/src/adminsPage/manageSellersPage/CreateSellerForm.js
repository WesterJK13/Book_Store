import {useFormik} from "formik";
import * as Yup from "yup";
import {useNavigate} from "react-router";

const CreateSellerForm = () => {

    const navigate = useNavigate();

    const formik = useFormik({
        initialValues: {
            email: '',
            name: '',
            patronymic: '',
            surname: '',
            birthDate: ''
        },
        validationSchema: Yup.object({
            email: Yup.string()
                .required("Поле обязательно для заполнения"),
            name: Yup.string()
                .required("Поле обязательно для заполнения"),
            surname: Yup.string()
                .required("Поле обязательно для заполнения"),
            birthDate: Yup.string()
                .required("Поле обязательно для заполнения"),
        }),
        onSubmit: async values => {
            const requestOptions = {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    email: values.email,
                    name: values.name,
                    patronymic: values.patronymic,
                    surname: values.surname,
                    birthDate: values.birthDate
                }),
            };

            try {
                const response = await fetch(`http://localhost:8081/api/v1/admin/createSeller`, requestOptions)
                if (response.ok) {
                    alert(`Продавец успешно создан`);
                    navigate('/sellers')
                } else {
                    alert('Данный email уже зарегистрирован в системе.');
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
                        Введите email продавца:
                    </p>
                    <input
                        type="email"
                        name='email'
                        className='input_auth fonts-roboto-light'
                        style={{fontSize: "18px"}}
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                        value={formik.values.email}
                    />
                    {formik.touched.email && formik.errors.email ? (
                        <div className='error-massage'>{formik.errors.email}</div>
                    ) : null}
                </div>

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

                <div className='field_block_in_form'>
                    <p className='some_info'>
                        Введите имя продавца:
                    </p>
                    <input
                        type="date"
                        name='birthDate'
                        className='input_auth fonts-roboto-light'
                        style={{fontSize: "18px"}}
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                        value={formik.values.birthDate}
                    />
                    {formik.touched.birthDate && formik.errors.birthDate ? (
                        <div className='error-massage'>{formik.errors.birthDate}</div>
                    ) : null}
                </div>

                <div className='form-button'>
                    <button className='button' type='submit'>
                        Создать
                    </button>
                </div>
            </form>
        </div>
    )

}

export default CreateSellerForm;