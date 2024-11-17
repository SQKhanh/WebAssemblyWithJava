use rand::Rng;

fn main() {
    let mut rng = rand::thread_rng();
    println!("Random number: {}", rng.gen_range(1..100));
}
