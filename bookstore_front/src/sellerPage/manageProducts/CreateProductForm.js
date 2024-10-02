import {useNavigate} from "react-router";
import {useFormik} from "formik";
import * as Yup from "yup";
import {useEffect, useState} from "react";

const CreateProductForm = () => {

    const navigate = useNavigate();
    const [authorsList, setAuthorsList] = useState([]);

    useEffect(() => {
        const fetchAuthors = async () => {
            const response = await fetch(`http://localhost:8081/api/v1/seller/getAllAuthors`);
            if (response.ok) {
                const result = await response.json();
                setAuthorsList(result);
            } else {
                console.error("Ошибка сервера");
            }
        };
        fetchAuthors();
    }, [])


    const formik = useFormik({
        initialValues: {
            name: '',
            definition: '',
            price: '',
            quantity: '',
            link: '',
            authors: []
        },
        validationSchema: Yup.object({
            name: Yup.string()
                .required("Поле обязательно для заполнения"),
            price: Yup.string()
                .required("Поле обязательно для заполнения"),
            quantity: Yup.string()
                .required("Поле обязательно для заполнения"),
            link: Yup.string()
                .required("Поле обязательно для заполнения"),
        }),
        onSubmit: async values => {
            const requestOptions = {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    name: values.name,
                    definition: values.definition,
                    price: values.price,
                    quantity: values.quantity,
                    link: values.link,
                    authors: values.authors.map(authorId => ({ id: authorId }))
                }),
            };

            try {
                // const formattedAuthors = values.authors.map(authorId => ({ id: authorId }));
                // const finalData = { ...values, authors: formattedAuthors };
                // console.log(finalData);
                // console.log(requestOptions.body);

                const response = await fetch(`http://localhost:8081/api/v1/seller/createProduct`, requestOptions)
                if (response.ok) {
                    alert(`Продукт успешно создан`);
                    navigate('/adminProducts')
                } else {
                    console.error("Ошибка сервера")
                }
            } catch (error) {
                console.error("Ошибка сети: " + error);
            }
        }
    })

    const handleAuthorChange = (event) => {
        const {options} = event.target;
        const selectedAuthors = [];
        for (let i = 0; i < options.length; i++) {
            if (options[i].selected) {
                selectedAuthors.push(options[i].value);
            }
        }
        formik.setFieldValue('authors', selectedAuthors);
    }


    return (
        <div>
            <form className='edit_form' onSubmit={formik.handleSubmit}>
                <div className='field_block_in_form'>
                    <p className='some_info'>
                        Введите наименование позиции товара:
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
                        Введите описание товара:
                    </p>
                    <input
                        type="text"
                        name='definition'
                        className='input_auth fonts-roboto-light'
                        style={{fontSize: "18px"}}
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                        value={formik.values.definition}
                    />
                </div>

                <div className='field_block_in_form'>
                    <p className='some_info'>
                        Введите цену позиции товара:
                    </p>
                    <input
                        type="number"
                        name='price'
                        className='input_auth fonts-roboto-light'
                        style={{fontSize: "18px"}}
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                        value={formik.values.price}
                    />
                    {formik.touched.price && formik.errors.price ? (
                        <div className='error-massage'>{formik.errors.price}</div>
                    ) : null}
                </div>

                <div className='field_block_in_form'>
                    <p className='some_info'>
                        Введите количество позиций товара на складе:
                    </p>
                    <input
                        type="number"
                        name='quantity'
                        className='input_auth fonts-roboto-light'
                        style={{fontSize: "18px"}}
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                        value={formik.values.quantity}
                    />
                    {formik.touched.quantity && formik.errors.quantity ? (
                        <div className='error-massage'>{formik.errors.quantity}</div>
                    ) : null}
                </div>

                <div className='field_block_in_form'>
                    <p className='some_info'>
                        Введите ссылку на изображение обложки товара:
                    </p>
                    <input
                        type="text"
                        name='link'
                        className='input_auth fonts-roboto-light'
                        style={{fontSize: "18px"}}
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                        value={formik.values.link}
                    />
                    {formik.touched.link && formik.errors.link ? (
                        <div className='error-massage'>{formik.errors.link}</div>
                    ) : null}
                </div>

                <div className='field_block_in_form'>
                    <p className='some_info'>
                        Выберите авторов: (при выборе удерживайте Shift для множественного выбора)
                    </p>
                    <select multiple size={10} onChange={handleAuthorChange} value={formik.values.authors} style={{width: "100%"}}>
                        {authorsList.map(author => (
                            <option key={author.id} value={author.id}>
                                {author.fio}
                            </option>
                        ))}
                    </select>
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

export default CreateProductForm;