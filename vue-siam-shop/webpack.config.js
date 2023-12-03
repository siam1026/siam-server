var path = require('path');
const webpack = require('webpack');
const MiniCssExtractPlugin = require("mini-css-extract-plugin")
const HtmlWebpackPlugin = require('html-webpack-plugin');
const VueLoaderPlugin = require('vue-loader/lib/plugin');
const CleanWebpackPlugin = require("clean-webpack-plugin");
const devMode = process.env.NODE_ENV !== 'production';
module.exports = {
    entry: ['babel-polyfill', path.join(__dirname, './src/main.js')], // 项目的入口文件，webpack会从main.js开始，把所有依赖的js都加载打包
    output: {
        path: path.resolve(__dirname, './dist'), // 项目的打包文件路径
        publicPath: '', // 通过devServer访问路径
        filename: 'js/[name].[hash].js' // 打包后的文件名
    },
    devServer: {
        historyApiFallback: true, //historyApiFallback设置为true那么所有的路径都执行index.html。
        overlay: true // 将错误显示在html之上
    },
    resolve: {
        alias: {
            'vue$': 'vue/dist/vue.esm.js'
        }
    },
    optimization:{ // +++
        splitChunks:{ // +++
            chunks:'initial' // +++ initial(初始块)、async(按需加载块)、all(全部块)
        }
    },
    plugins: [
        new CleanWebpackPlugin(),
        new HtmlWebpackPlugin(
            {
                template: 'index.html',
                filename: 'index.html',
                favicon: './favicon.ico',
                minify: {
                    removeComments: true,
                    collapseWhitespace: true,
                    removeAttributeQuotes: true
                    // more options:
                    // https://github.com/kangax/html-minifier#options-quick-reference
                }
            }
        ),
        new MiniCssExtractPlugin({
            filename: "css/[name].[hash:8].css",
        }),
        new VueLoaderPlugin(),
        new webpack.ProvidePlugin({
            'window.Quill': 'quill/dist/quill.js',
            'Quill': 'quill/dist/quill.js'
        })
    ],
    module: {
        rules: [
            {
                test: /\.vue$/,
                use:[
                    'vue-loader'
                ],
            },
            {
                test: /\.css$/,
                use: [
                    "style-loader",
                    MiniCssExtractPlugin.loader,
                    "css-loader"
                ]
            },
            {
                test: /\.scss$/,
                use: [   
                    {
                        loader: MiniCssExtractPlugin.loader,
                        options: {
                            publicPath: '../'
                        }
                    },
                    'css-loader',   //注意顺序
                    'sass-loader'
                ]
            },
            {
                test: /\.jsx?$/,
                use: [
                    {
                        loader: 'babel-loader',
                        options: { presets: ['es2015'] }
                    }
                ],
                exclude: /node_modules/,  //不去检查node_modules里的内容，那里的js太多了，会非常慢
                include: path.resolve(__dirname, 'src/js'),   //直接规定查找的范围
            },
            {
                test: /\.(png|jpg|jpeg|gif|eot|ttf|woff|woff2|svg|svgz)(\?.+)?$/,
                use: [
                    {
                        loader: 'url-loader',    //把图片转成base64
                        options: {
                            limit: 50 * 1024,  //小于50k就会转成base64
                            outputPath: 'images'
                        }
                    }
                ]
                //use:'url-loader?limit=50000&outputPath=images'    //loader的另一种写法，与get请求方式相同
            }
        ]
    },
    devServer: {
        contentBase: path.join(__dirname, "dist"),
        host: 'localhost',   
        port: 8001,  //端口
        open: true,  //自动打开页面
        hot: true,   //开启热更新
        inline: true,
        overlay: true, // 编译出错的时候，在浏览器页面上显示错误，默认是false，可设置为true
        stats: "normal", // 来控制编译的时候shell上的输出内容
    }
}
