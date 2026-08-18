// utils/babel.config.js
module.exports = {
  presets: [
    [
      '@vue/cli-plugin-babel/preset',
      {
        useBuiltIns: 'entry',
        corejs: 3
      }
    ]
  ],
  plugins: [
    '@babel/plugin-proposal-optional-chaining',
    '@babel/plugin-proposal-nullish-coalescing-operator',
    '@babel/plugin-proposal-logical-assignment-operators'
  ],
  // 关键：强制转译node_modules中的现代语法
  overrides: [
    {
      include: /node_modules\/marked/,
      presets: ['@babel/preset-env']
    }
  ]
}