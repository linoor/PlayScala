var webpack = require('webpack');
var path = require('path');

var BUILD_DIR = path.resolve(__dirname, 'public/javascripts');
var APP_DIR = path.resolve(__dirname, 'app/views');

var config = {
    entry: {
        index: APP_DIR + '/index.jsx',
        cart: APP_DIR + '/cart.jsx',
        order: APP_DIR + '/order.jsx'
    },
    output: {
        path: BUILD_DIR,
        filename: '[name].js'
    },
    module: {
        loaders: [
            {
                test: /\.jsx?/,
                include: APP_DIR,
                loader: 'babel'
            }
        ]
    },
    resolve: {
        root: path.resolve(BUILD_DIR),
        extensions: ['', '.js']
    }
};

module.exports = config;
