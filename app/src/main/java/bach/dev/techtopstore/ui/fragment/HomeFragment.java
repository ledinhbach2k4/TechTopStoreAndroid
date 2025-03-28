package bach.dev.techtopstore.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import bach.dev.techtopstore.R;
import bach.dev.techtopstore.data.dto.CategoryDto;
import bach.dev.techtopstore.data.dto.ProductDto;
import bach.dev.techtopstore.ui.adapter.CategoryAdapter;
import bach.dev.techtopstore.ui.adapter.ProductAdapter;
import bach.dev.techtopstore.ui.constract.HomeConstract;
import bach.dev.techtopstore.ui.presenter.HomePresenter;

public class HomeFragment extends Fragment implements HomeConstract.View, ProductAdapter.OnItemClickListener {
    private RecyclerView rvCategory;
    private RecyclerView rvProduct;
    private HomeConstract.Presenter mPresenter;
    private EditText edtSearch;
    private Button btnSearch;
    private ProgressBar progressBar;
    private ProductAdapter productAdapter;
    private CategoryAdapter categoryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initGUI(rootView);
        initData();
        return rootView;
    }

    private void initGUI(View rootView) {
        edtSearch = rootView.findViewById(R.id.edt_search);
        btnSearch = rootView.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(view -> {
            if (mPresenter != null) {
                mPresenter.searchProduct(edtSearch.getText().toString());
            }
        });

        progressBar = rootView.findViewById(R.id.progressBar);

        rvCategory = rootView.findViewById(R.id.rv_category);
        rvProduct = rootView.findViewById(R.id.rv_product);
        rvCategory.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rvProduct.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        // Khởi tạo adapter một lần
        productAdapter = new ProductAdapter(new ArrayList<>(), this);
        rvProduct.setAdapter(productAdapter);

        categoryAdapter = new CategoryAdapter(new ArrayList<>(), category -> {
            if (mPresenter != null) {
                mPresenter.getProductsByCategory(category.getId());
            }
        });
        rvCategory.setAdapter(categoryAdapter);
    }

    private void initData() {
        mPresenter = new HomePresenter(getContext()); // Truyền Context cho HomePresenter
        mPresenter.setView(this);
        mPresenter.getCategories();
        mPresenter.getProducts();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProducts(List<ProductDto> productList) {
        productAdapter.updateProducts(productList); // Cập nhật dữ liệu thay vì tạo mới adapter
    }

    @Override
    public void showCategories(List<CategoryDto> categories) {
        categoryAdapter.updateCategories(categories);
    }

    @Override
    public void onItemClick(int productId) {
        if (mPresenter != null) {
            mPresenter.onProductClicked(productId);
        }
    }
}