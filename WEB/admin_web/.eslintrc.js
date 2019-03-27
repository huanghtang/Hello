// https://eslint.org/docs/user-guide/configuring

module.exports = {
    root: true,
    parser: 'babel-eslint',
    parserOptions: {
        sourceType: 'module'
    },
    env: {
        browser: true,
    },
    // https://github.com/standard/standard/blob/master/docs/RULES-en.md
    extends: 'standard',
    // required to lint *.vue files
    plugins: [
        'html'
    ],
    // add your custom rules here
    rules: {
        "standard/object-curly-even-spacing": 0,
        // allow paren-less arrow functions
        'arrow-parens': 0,
        'spaced-comment': 0,
        // 关闭语句强制分号结尾
        "semi": [0],
        //空行最多不能超过100行
        "indent": 0, //缩进风格
        "no-multiple-empty-lines": [0, { "max": 100 }],
        //关闭禁止混用tab和空格
        "no-mixed-spaces-and-tabs": [0],
        "space-before-function-paren": 0,
        "quotes": 0,
        // allow async-await
        'generator-star-spacing': 0,
        // allow debugger during development
        'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off'
    }
}