const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const favouriteSchema = new Schema({
    userId: { type: mongoose.Schema.Types.ObjectId, ref: "User", required: true },
    productId: { type: mongoose.Schema.Types.ObjectId, ref: "Product", required: true },
    status: { type: Number, required: true, default: 1 } // 1: Đã yêu thích, 0: Bỏ yêu thích
}, {
    timestamps: true
});

module.exports = mongoose.model('Favourite', favouriteSchema);
