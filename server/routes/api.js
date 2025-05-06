const express = require("express");
const router = express.Router();
const mongoose = require('mongoose');
const { OAuth2Client } = require('google-auth-library');
const client = new OAuth2Client(process.env.GOOGLE_CLIENT_ID);
const User = require("../models/users");
const Product = require("../models/products");
const Category = require("../models/category");
const Favourite = require("../models/favourite");

router.get("/users", async (req, res) => {
    try {
        const users = await User.find();
        res.status(200).json(users);
    } catch (err) {
        res.status(500).json({ error: "Lỗi server khi lấy danh sách user" });
    }
});

router.get("/getuserbyid/:id", async (req, res) => {
  try {
    const { id } = req.params;
    const user = await User.findById(id);
    if (user) {
      res.status(200).json({
        status: 200,
        message: "User retrieved successfully",
        data: user,
      });
    } else {
      res.status(404).json({ message: "User not found" });
    }
  } catch (error) {
    console.error("Error:", error);
    res
      .status(500)
      .json({ message: "An error occurred while retrieving user" });
  }
});

router.post("/login", async (req, res) => {
  try {
    const { email, password } = req.body;
    const user = await User.findOne({  email, password  });

    if (!user) {
      return res.status(400).json({ message: "Tên đăng nhập không tồn tại" });
    }

    // So sánh mật khẩu mà không sử dụng bcrypt
    if (password !== user.password) {
      return res.status(400).json({ message: "Mật khẩu không chính xác" });
    }

    // const token = jwt.sign({ id: user._id, role: user.role }, SECRET_KEY, {
    //   expiresIn: "1h",
    // });
    // const refreshToken = jwt.sign({ id: user._id }, SECRET_KEY, {
    //   expiresIn: "1d",
    // });

    res.status(200).json({
      status: 200,
      message: "Login thành công",
      id: user._id,
    //   token: token,
    //   refreshToken: refreshToken,
    });
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: "Lỗi máy chủ nội bộ" });
  }
});
router.post('/login/google', async (req, res) => {
  const { idToken } = req.body;

  
  try {
    // Xác thực token
    if (!idToken) {
      return res.status(400).json({ message: 'ID Token is required' });
    }
    const ticket = await client.verifyIdToken({
      idToken: idToken,
      audience: process.env.GOOGLE_CLIENT_ID,
    });
    const payload = ticket.getPayload();
    const { sub, email, name, picture } = payload;
    console.log(sub);

    // Kiểm tra xem người dùng đã tồn tại trong MongoDB chưa
    let user = await User.findOne({ email: email });
    if (!user) {
      // Nếu chưa tồn tại, tạo người dùng mới
      user = new User({
        email:email,
        name,
        address: "",
        avatarUrl: picture
      });
      await user.save();
    }

    // // Tạo token JWT cho phiên đăng nhập
    // const accessToken = jwt.sign({ id: user._id, role: user.role }, SECRET_KEY, { expiresIn: '1h' });

    res.status(200).json({
      status: 200,
      message: 'Login successful',
      data: user
    });
  } catch (error) {
    console.error('Google login error:', error);
    res.status(500).json({ message: 'Login failed', error: error.message });
  }
});

router.get('/products', async (req, res) => {
  try { 
    const products = await Product.find();
    res.status(200).json({
      status: 200,
      message: 'Products fetched successfully',
      data: products, 
    });
  } catch (error) {
    console.error("Error fetching Products:", error);
    res.status(500).json({
      message: 'An error occurred while fetching Products',
    });
  }
});

router.get('/products/:id', async (req, res) => {
  const { id } = req.params;
  try {
    const product = await Product.findById(id);
    if (!product) {
      return res.status(404).json({
        status: 404,
        message: 'Không tìm thấy sản phẩm với ID này',
      });
    }
    res.status(200).json({
      status: 200,
      message: 'Lấy sản phẩm thành công',
      data: product,
    });
  } catch (error) {
    console.error("Lỗi khi lấy sản phẩm theo ID:", error);
    res.status(500).json({
      status: 500,
      message: 'Lỗi máy chủ khi lấy sản phẩm theo ID',
    });
  }
});
router.get('/categories', async (req, res) => {
    try { 
      const categories = await Category.find();
      res.status(200).json({
        status: 200,
        message: 'categories fetched successfully',
        data: categories, 
      });
    } catch (error) {
      console.error("Error fetching categories:", error);
      res.status(500).json({
        message: 'An error occurred while fetching categories',
      });
    }
  });
  router.get('/products/category/:categoryId', async (req, res) => {
    const { categoryId } = req.params;
    try {
      const products = await Product.find({ category: categoryId });

      if (!products || products.length === 0) {
        return res.status(404).json({ message: "Không tìm thấy sản phẩm thuộc thể loại này" });
      }
  
      res.status(200).json({
        status: 200,
        message: 'Lấy sản phẩm theo thể loại thành công',
        data: products,
      });
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: 'Lỗi khi lọc sản phẩm theo thể loại' });
    }
  });

  // favourite
  router.get('/favourites', async (req, res) => {
    try { 
      const favourites = await Favourite.find();
      res.status(200).json({
        status: 200,
        message: 'favourites fetched successfully',
        data: favourites, 
      });
    } catch (error) {
      console.error("Error fetching favourites:", error);
      res.status(500).json({
        message: 'An error occurred while fetching favourites',
      });
    }
  });
  
//add product for 1 per
router.post("/user/:userId/add_favourites", async (req, res) => {
  try {
    const { userId } = req.params;
    const { productId } = req.body;

    const newFavourite = new Favourite({
      userId,
      productId,
    });

    const result = await newFavourite.save();
    res.status(201).json({
      status: 201,
      message: "Favourite added to user successfully",
      data: result,
    });
  } catch (error) {
    console.error("Error:", error);
    res
      .status(500)
      .json({
        message: "An error occurred while adding Favourite to User",
        error: error.message,
      });
  }
});
router.get('/favourites/check', async (req, res) => {
  const { userId, productId } = req.query;
  try {
    const favourite = await Favourite.findOne({ userId, productId });
    if (favourite) {
      return res.status(200).json({ message:"success",status: 200, data: favourite });
    } else {
      return res.status(200).json({ message:"failed",status: 200,data: [], message: "Favourite not found" });
    }
  } catch (err) {
    res.status(500).json({ status: 500, message: err.message });
  }
});
router.delete("/delete_Favourite/:userId/:productId", async (req, res) => {
  try {
    const { userId, productId } = req.params;

    const deletedFavourite = await Favourite.findOneAndDelete({ userId, productId });

    if (!deletedFavourite) {
      return res.status(404).json({
        status: 404,
        message: "Favourite not found",
      });
    }
    res.status(200).json({
      status: 200,
      message: "Favourite removed successfully",
    });
  } catch (error) {
    res.status(500).json({
      status: 500,
      message: error.message,
    });
  }
});


// GET /user/:userId/favourites
router.get('/user/:userId/favourites', async (req, res) => {
  const { userId } = req.params;
console.log(userId);

  try {
    // Populate để lấy chi tiết sản phẩm luôn
    const favourites = await Favourite.find({ userId }).populate('productId');

    if (!favourites || favourites.length === 0) {
      return res.status(200).json({
        status: 200,
        message: "Người dùng chưa có sản phẩm yêu thích nào",
        data:[]
      });
    }

    res.status(200).json({
      status: 200,
      message: "Lấy danh sách sản phẩm yêu thích thành công",
      data: favourites.map(fav => fav.productId), // Chỉ trả về danh sách sản phẩm
    });
  } catch (error) {
    console.error("Lỗi khi lấy danh sách yêu thích theo user:", error);
    res.status(500).json({
      status: 500,
      message: "Lỗi server khi lấy danh sách sản phẩm yêu thích",
      error: error.message,
    });
  }
});

module.exports = router;
