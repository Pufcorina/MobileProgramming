let express = require('express');
let router = express.Router();
const pool = require("../database");

router.get('/shows', function(req, res, next) {
    let sql = 'SELECT * FROM Shows';
    pool.query(sql, function (err, result) {
        if (err) throw err;
        res.json(result)
    });
});


module.exports = router;
