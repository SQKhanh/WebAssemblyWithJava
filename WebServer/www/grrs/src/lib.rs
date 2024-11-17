use wasm_bindgen::prelude::*;
use web_sys::{window, HtmlCanvasElement};

#[wasm_bindgen(start)]
pub fn start() -> Result<(), JsValue> {
    // Lấy đối tượng window
    let window = window().ok_or("no global window exists")?;
    
    // Lấy đối tượng canvas từ HTML
    let document = window.document().ok_or("should have a document on window")?;
    let canvas = document
        .get_element_by_id("my_canvas")
        .ok_or("should have a canvas on the page")?
        .dyn_into::<HtmlCanvasElement>()?;

    // Thiết lập context 2d để vẽ
    let context = canvas
        .get_context("2d")?
        .ok_or("should have 2d context")?
        .dyn_into::<web_sys::CanvasRenderingContext2d>()?;

    // Vẽ hình chữ nhật ban đầu
    draw(&context, 1.0);

    Ok(())
}

// Hàm vẽ với tỷ lệ zoom
fn draw(context: &web_sys::CanvasRenderingContext2d, scale: f64) {
    context.clear_rect(0.0, 0.0, 500.0, 500.0); // Xóa canvas
    context.set_fill_style(&"blue".into());
    
    // Vẽ một hình chữ nhật với kích thước được nhân với tỷ lệ zoom
    context.fill_rect(50.0 * scale, 50.0 * scale, 400.0 * scale, 400.0 * scale);
}
