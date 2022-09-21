const { default: axios } = require('axios');
const express = require('express');

const app = express();
app.set('view engine', 'ejs');
app.use(express.urlencoded({ extended: false }));
const port = process.env.APP_PORT || 3000;

app.get('/', (req, res) => {
    res.render('index');
})

app.get('/home', (req, res) => {
    res.render('index');
})

app.get('/login', (req, res) => {
    res.render('login');
})

app.get('/new-account', (req, res) => {
    res.render('new-account');
})

app.post('/new-account', async (req, res) => {
    const account = {
        name: req.body.name,
        document: req.body.document,
        birthdate: req.body.birthdate,
        phone: req.body.phone,
        email: req.body.email,
        cep: req.body.cep,
        number: req.body.number,
        secondAddress: req.body.secondAddress,
        password: req.body.password
    }

    try {
        const response = await axios.post('http://localhost:8080/api/create-account', account);
        if (response.status == 200 || response.status == 201)
            res.render('success', { message: `Conta criado com sucesso, o numero da conta é: ${response.data.number}` });
        else 
            res.render('error', { message: response.data });

    } catch (err) {
        res.render('error', { message: err.response.data });
    }
})

app.post('/login', async (req, res) => {
    const access = {
        accountNumber: req.body.accountNumber,
        password: req.body.password
    }

    const response = await axios.post('http://localhost:8080/api/login', access);

    const pageRedirect = response.status == 200 || response.status == 200 ? 'menu' : 'error';

    res.render(pageRedirect, { message: response.data });
})


app.listen(port, () => {
    console.log(`bank system frontend is working on port: ${port}`);
})
