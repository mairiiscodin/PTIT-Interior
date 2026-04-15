package Model;

import java.util.HashMap;
import java.util.Map;

public class Cart {
	// Dùng Map với Key là ProductID để tìm kiếm nhanh khi update

	private Map<String, CartItem> items;

	public Cart() {
		this.items = new HashMap<>();
	}

	public Map<String, CartItem> getItems() {
		return items;
	}

	// Logic cập nhật số lượng (Dùng cho Ajax Update)
	public void updateItem(String productId, int quantity) {
		if (items.containsKey(productId)) {
			items.get(productId).setQuantity(quantity);
		}
	}

	// Tính tổng tiền toàn bộ giỏ: JSTL gọi ${cart.totalFormatted}
	public String getTotalFormatted() {
		double total = 0;
		for (CartItem item : items.values()) {
			total += item.getProduct().getPrice() * item.getQuantity();
		}
		return String.format("%,.0f", total);
	}

//    // Hàm này dùng để Servlet lấy giá trị trả về cho Ajax
//    public String getItemSubtotal(String productId) {
//        return items.get(productId).getSubtotalFormatted();
//    }
}
