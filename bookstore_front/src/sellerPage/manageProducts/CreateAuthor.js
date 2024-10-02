import Navbar from "../../navbar/Navbar";
import PageName from "../../pages/PageName";
import {useFormik} from "formik";
import * as Yup from "yup";
import {useNavigate} from "react-router";

const CreateAuthor = () => {

    const navigate = useNavigate();

    const formik = useFormik({
        initialValues: {
            fio: ''
        },
        validationSchema: Yup.object({
            fio: Yup.string()
                .required("Поле обязательно для заполнения")
        }),
        onSubmit: async values => {
            const requestOptions = {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    fio: values.fio,
                }),
            };

            try {
                const response = await fetch(`http://localhost:8081/api/v1/seller/createAuthor`, requestOptions)
                if (response.ok) {
                    alert("Автор успешно добавлен в систему");
                    navigate('/adminProducts')
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
            <Navbar/>
            <div className='page-content'>
                <PageName name={'Создание автора'}/>
                <div>
                    <form className='edit_form' onSubmit={formik.handleSubmit}>
                        <div className='field_block_in_form'>
                            <p className='some_info'>
                                Введите ФИО автора:
                            </p>
                            <input
                                type="text"
                                name='fio'
                                className='input_auth fonts-roboto-light'
                                style={{fontSize: "18px"}}
                                onChange={formik.handleChange}
                                onBlur={formik.handleBlur}
                                value={formik.values.fio}
                            />
                            {formik.touched.fio && formik.errors.fio ? (
                                <div className='error-massage'>{formik.errors.fio}</div>
                            ) : null}
                        </div>

                        <div className='form-button'>
                            <button className='button' type='submit'>
                                Создать
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    )
}

export default CreateAuthor;