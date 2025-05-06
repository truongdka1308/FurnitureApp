const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const productSchema = new Schema({
    name: { type: String, required: true },
    price: { type: Number, required: true }, 
    description: { type: String, required: true },
    rating: { type: String, required: true },
    category: { type: mongoose.Schema.Types.ObjectId, ref: 'Category', required: true }, // ref viết đúng
    img: [{ type: String, required: true }]
}, {
    timestamps: true
});

module.exports = mongoose.model('Product', productSchema); // Viết hoa 'Product'
