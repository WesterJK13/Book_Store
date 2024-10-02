import {useContext} from "react";
import {CustomContext} from "../utils/Context";
import {useNavigate} from "react-router";
import {useFormik} from "formik";
import * as Yup from 'yup';

const RegistrationForm = () => {

    const {user, setUser} = useContext(CustomContext);
    const navigate = useNavigate();

    const formik = useFormik({
        initialValues: {
            email: '',
            password: '',
            name: '',
            surname: '',
            patronymic: '',
            phone: '',
        },
        validationSchema: Yup.object({
            email: Yup.string()
                .required("Поле обязательно для заполнения"),
            password: Yup.string()
                .required("Поле обязательно для заполнения"),
            name: Yup.string()
                .required("Поле обязательно для заполнения"),
            surname: Yup.string()
                .required("Поле обязательно для заполнения"),
            phone: Yup.string()
                .required("Поле обязательно для заполнения"),
        }),
        onSubmit: async values => {
            const requestOptions = {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    email: values.email,
                    password: values.password,
                    name: values.name,
                    surname: values.surname,
                    patronymic: values.patronymic,
                    phone: values.phone
                }),
            };

            try {
                const response = await fetch('http://localhost:8081/api/v1/auth/signup', requestOptions)
                if (response.ok) {
                    const result = await response.json();
                    setUser({
                        ...result
                    })
                    localStorage.setItem('user', JSON.stringify({
                        ...result
                    }))
                    navigate('/')
                } else {
                    const errorData = await response.json();
                    console.error("Ошибка сервера: ", errorData);
                }
            } catch (error) {
                console.error("Ошибка сети: " + error);
            }
        }
    })

    return (
        <div className='authorization'>
            <div className='popup'>
                <form className='form' onSubmit={formik.handleSubmit}>
                    <div>
                        <p className='header fonts-roboto-black'>Регистрация</p>
                        <input
                            name='email'
                            type='email'
                            className='input_auth fonts-roboto-light'
                            placeholder='Введите логин'
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            value={formik.values.email}
                        />
                        {formik.touched.email && formik.errors.email ? (
                            <div className='error-massage'>{formik.errors.email}</div>
                        ) : null}
                    </div>

                    <div>
                        <input
                            name='password'
                            type='password'
                            className='input_auth fonts-roboto-light'
                            placeholder='Введите пароль'
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            value={formik.values.password}
                        />
                        {formik.touched.password && formik.errors.password ? (
                            <div className='error-massage'>{formik.errors.password}</div>
                        ) : null}
                    </div>

                    <div>
                        <input
                            name='name'
                            type='text'
                            className='input_auth fonts-roboto-light'
                            placeholder='Введите имя'
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            value={formik.values.name}
                        />
                        {formik.touched.name && formik.errors.name ? (
                            <div className='error-massage'>{formik.errors.name}</div>
                        ) : null}
                    </div>

                    <div>
                        <input
                            name='patronymic'
                            type='text'
                            className='input_auth fonts-roboto-light'
                            placeholder='Введите отчество'
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            value={formik.values.patronymic}
                        />
                        {formik.touched.patronymic && formik.errors.patronymic ? (
                            <div className='error-massage'>{formik.errors.patronymic}</div>
                        ) : null}
                    </div>

                    <div>
                        <input
                            name='surname'
                            type='text'
                            className='input_auth fonts-roboto-light'
                            placeholder='Введите фамилию'
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            value={formik.values.surname}
                        />
                        {formik.touched.surname && formik.errors.surname ? (
                            <div className='error-massage'>{formik.errors.surname}</div>
                        ) : null}
                    </div>

                    <div>
                        <input
                            name='phone'
                            type='tel'
                            className='input_auth fonts-roboto-light'
                            placeholder='Введите телефон'
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
                        <p className='fonts-roboto-regular clue'>Уже есть аккаунт? <a href='/login' className='link'>Вход</a></p>
                        <p className='fonts-roboto-regular clue'><a href='/' className='link'>Главная</a></p>
                    </div>

                </form>
            </div>
        </div>
    )

}

export default RegistrationForm;