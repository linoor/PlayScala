var webpack = require('webpack');
var path = require('path');

var BUILD_DIR = path.resolve(__dirname, 'public/javascripts');
var APP_DIR = path.resolve(__dirname, 'app/views');

var config = {
    entry: {
        index: APP_DIR + '/index.jsx',
        signup: APP_DIR + '/signup.jsx'
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
    }
};

module.exports = config;
